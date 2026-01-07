package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ngkafisha.application.identityService.userContext.useCases.groupUseCases.GetAllGroupUseCase
import com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases.GetCurrentStudent
import com.example.ngkafisha.application.identityService.userContext.useCases.studentUseCases.UpdateStudentUseCase
import com.example.ngkafisha.domain.identityService.userContext.models.Group
import com.example.ngkafisha.domain.identityService.userContext.models.Student
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStudentProfileViewModel @Inject constructor(
    private val getCurrentStudent: GetCurrentStudent,
    private val getAllGroupUseCase: GetAllGroupUseCase,
    private val updateStudentUseCase: UpdateStudentUseCase
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState: StateFlow<ActualState> = _actualState

    private val _studentState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val studentState: StateFlow<ActualState> = _studentState

    private val _student = MutableStateFlow<Student?>(null)
    val student: StateFlow<Student?> = _student

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    fun loadData() {
        viewModelScope.launch {
            _actualState.value = ActualState.Loading

            val studentResult = getCurrentStudent(GetCurrentStudent.Request())
            val groupsResult = getAllGroupUseCase(Unit)

            if (!studentResult.isSuccess || !groupsResult.isSuccess) {
                _actualState.value =
                    ActualState.Error("Ошибка загрузки профиля студента")
                return@launch
            }

            _student.value = studentResult.value
            _groups.value = groupsResult.value!!

            _actualState.value = ActualState.Success("")
        }
    }

    fun updateGroup(group: Group) {
        _student.value = _student.value?.copy(
            groupId = group.groupId,
            group = group
        )
    }

    fun saveChanges() {
        val currentStudent = _student.value ?: return

        viewModelScope.launch {
            _studentState.value = ActualState.Loading

            val result = updateStudentUseCase(
                UpdateStudentUseCase.Request(currentStudent)
            )

            _studentState.value =
                if (result.isSuccess) {
                    ActualState.Success("")
                } else {
                    ActualState.Error(
                        result.errorMessage
                    )
                }
        }
    }
}