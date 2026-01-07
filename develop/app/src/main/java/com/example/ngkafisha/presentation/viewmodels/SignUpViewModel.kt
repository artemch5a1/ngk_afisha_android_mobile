package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.application.identityService.accountContext.useCases.RegistryStudentUseCase
import com.example.application.identityService.userContext.useCases.groupUseCases.GetAllGroupUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.userContext.models.Group
import com.example.ngkafisha.presentation.mapper.StudentMapper
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.models.states.StudentSignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registryStudentUseCase: RegistryStudentUseCase,
    private val getAllGroupUseCase: GetAllGroupUseCase
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val actualState: StateFlow<ActualState> = _actualState.asStateFlow()

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> get() = _groups

    private val _studentSignUpState = mutableStateOf(StudentSignUpState())
    val studentSignUpState: StudentSignUpState get() = _studentSignUpState.value

    init {
        loadGroups()
    }

    fun updateSign(newSign: StudentSignUpState){
        _studentSignUpState.value = newSign
    }


    fun loadGroups()
    {
        viewModelScope.launch {
            val result: CustomResult<List<Group>> = getAllGroupUseCase(Unit)

            if(!result.isSuccess || result.value == null){
                _actualState.value = ActualState.Error("Не удалось загрузить группы")
                return@launch
            }

            _groups.value = result.value
        }
    }

    fun signUp(controlNav: NavHostController) {
        _actualState.value = ActualState.Loading
        viewModelScope.launch{

            val customResult: CustomResult<Unit> = registryStudentUseCase(
                RegistryStudentUseCase.Request(
                    studentSignUpState.email,
                    studentSignUpState.password,
                    repeatPassword = studentSignUpState.repeatPassword,
                    StudentMapper.toStudent(studentSignUpState)
                )
            )

            if(customResult.isSuccess)
            {
                _actualState.value = ActualState.Success("")
                controlNav.navigate("signIn") {
                    popUpTo("signUp") { inclusive = true }
                }
                return@launch
            }

            _actualState.value = ActualState.Error(customResult.errorMessage)
        }
    }
}