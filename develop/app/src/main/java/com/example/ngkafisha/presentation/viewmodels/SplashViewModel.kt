package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.identityService.accountContext.useCases.ValidateTokenUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import com.example.ngkafisha.presentation.settings.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionInfoStore: SessionInfoStore,
    private val sessionRepository: SessionRepository,
    private val validateTokenUseCase: ValidateTokenUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _shouldNavigateToLogin = MutableStateFlow(false)
    val shouldNavigateToLogin: StateFlow<Boolean> = _shouldNavigateToLogin.asStateFlow()

    private val _shouldNavigateToHome = MutableStateFlow(false)
    val shouldNavigateToHome: StateFlow<Boolean> = _shouldNavigateToHome.asStateFlow()

    fun checkSession() {
        viewModelScope.launch {
            _isLoading.value = true

            val savedToken = sessionRepository.getAccessToken()

            if (savedToken != null && sessionInfoStore.isAuth) {
                _shouldNavigateToHome.value = true
                _isLoading.value = false
                return@launch
            }

            if (savedToken != null) {
                val result: CustomResult<Account> = validateTokenUseCase(
                    ValidateTokenUseCase.Request(savedToken)
                )

                if (result.isSuccess && result.value != null) {
                    val account = result.value!!
                    val accountSession = AccountSession(
                        account = account,
                        accessToken = savedToken
                    )

                    sessionInfoStore.setSessionWithSave(accountSession)
                    _shouldNavigateToHome.value = true
                } else {
                    sessionRepository.clearToken()
                    _shouldNavigateToLogin.value = true
                }
            } else {
                _shouldNavigateToLogin.value = true
            }

            _isLoading.value = false
        }
    }
}
