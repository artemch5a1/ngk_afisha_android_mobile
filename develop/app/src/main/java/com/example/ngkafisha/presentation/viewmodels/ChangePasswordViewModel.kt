package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.identityService.accountContext.useCases.ChangePasswordUseCase
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ActualState>(ActualState.Initialized)
    val state = _state.asStateFlow()

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        repeatPassword: String
    ) {
        viewModelScope.launch {
            _state.value = ActualState.Loading

            val result = changePasswordUseCase(
                ChangePasswordUseCase.Request(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                    repeatPassword = repeatPassword
                )
            )

            if (!result.isSuccess) {
                _state.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _state.value = ActualState.Success("Пароль успешно изменён")
        }
    }
}