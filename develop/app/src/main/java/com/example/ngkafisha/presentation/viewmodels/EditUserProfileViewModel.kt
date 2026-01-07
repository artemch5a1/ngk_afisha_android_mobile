package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ngkafisha.application.identityService.userContext.useCases.userUseCases.GetCurrentUser
import com.example.ngkafisha.application.identityService.userContext.useCases.userUseCases.UpdateUserInfoUseCase
import com.example.ngkafisha.domain.identityService.userContext.models.User
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserProfileViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState = _actualState.asStateFlow()

    private val _userState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val userState = _userState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    val textAction = MutableLiveData("Сохранить изменения")
    val textPage = MutableLiveData("Редактирование профиля")

    fun loadUser() {
        viewModelScope.launch {
            _actualState.value = ActualState.Loading

            val result = getCurrentUser(Unit)
            if (!result.isSuccess) {
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _user.value = result.value
            _actualState.value = ActualState.Success("")
        }
    }

    fun updateUser(updatedUser: User) {
        _user.value = updatedUser
    }

    fun saveChanges() {
        val currentUser = _user.value ?: return

        viewModelScope.launch {
            _userState.value = ActualState.Loading

            val result = updateUserInfoUseCase(
                UpdateUserInfoUseCase.Request(currentUser)
            )

            if (!result.isSuccess) {
                _userState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _userState.value = ActualState.Success("")
        }
    }
}