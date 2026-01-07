package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ngkafisha.application.identityService.accountContext.useCases.GetCurrentAccount
import com.example.ngkafisha.application.identityService.accountContext.useCases.LogoutUseCase
import com.example.ngkafisha.application.identityService.userContext.useCases.userUseCases.GetCurrentUser
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.models.Account
import com.example.ngkafisha.domain.identityService.userContext.models.User
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getCurrentAccount: GetCurrentAccount,
    private val getCurrentUser: GetCurrentUser,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ActualState>(ActualState.Loading)
    val state = _state.asStateFlow()

    private val _account = MutableStateFlow<Account?>(null)
    val account = _account.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _state.value = ActualState.Loading

            val accResult: CustomResult<Account> = getCurrentAccount(Unit)
            if (!accResult.isSuccess) {
                _state.value = ActualState.Error(accResult.errorMessage)
                return@launch
            }
            _account.value = accResult.value

            val userResult: CustomResult<User> = getCurrentUser(Unit)
            if (!userResult.isSuccess) {
                _state.value = ActualState.Error(userResult.errorMessage)
                return@launch
            }
            _user.value = userResult.value

            _state.value = ActualState.Success("")
        }
    }

    fun logout()
    {
        viewModelScope.launch {
            logoutUseCase(Unit)
        }
    }
}