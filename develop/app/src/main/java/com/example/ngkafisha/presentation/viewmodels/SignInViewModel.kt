package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ngkafisha.application.identityService.accountContext.useCases.LoginUseCase
import com.example.ngkafisha.domain.identityService.accountContext.models.AccountSession
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.models.states.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.ngkafisha.domain.common.models.CustomResult

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val actualState: StateFlow<ActualState> = _actualState.asStateFlow()

    private val _signInState = mutableStateOf(SignInState())
    val signInState: SignInState get() = _signInState.value

    fun updateSign(newSign: SignInState){
        _signInState.value = newSign
    }

    fun setErrorState(message: String){

        _actualState.value = ActualState.Error(message)

    }

    fun signIn(controlNav: NavHostController) {
        _actualState.value = ActualState.Loading
        viewModelScope.launch{

            val customResult: CustomResult<AccountSession> =
                loginUseCase(LoginUseCase.Request(signInState.email, signInState.password))

            if(customResult.isSuccess)
            {
                _actualState.value = ActualState.Success("")
                controlNav.navigate("listEventScreen") {
                    popUpTo("signIn") { inclusive = true }
                }
                return@launch
            }

            _actualState.value = ActualState.Error(customResult.errorMessage)
        }
    }
}