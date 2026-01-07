package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.enums.Role
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases.GetCurrentStudent
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
    private val sessionInfoStore: SessionInfoStore,
    private val getCurrentStudent: GetCurrentStudent
) : ViewModel() {

    private val _state = MutableStateFlow<ActualState>(ActualState.Loading)
    val state = _state.asStateFlow()

    private val _student = MutableStateFlow<Student?>(null)
    val student = _student.asStateFlow()

    fun loadStudentProfile() {
        viewModelScope.launch {
            _state.value = ActualState.Loading

            val account = sessionInfoStore.currentAccount
            if (account == null || account.accountRole != Role.User) {
                _state.value = ActualState.Error("Доступ запрещён")
                return@launch
            }

            val result = getCurrentStudent(
                GetCurrentStudent.Request()
            )

            if (!result.isSuccess) {
                _state.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _student.value = result.value
            _state.value = ActualState.Success("")
        }
    }
}