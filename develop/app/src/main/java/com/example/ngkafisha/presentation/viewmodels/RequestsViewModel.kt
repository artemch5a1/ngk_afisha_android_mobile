package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.enums.Role
import com.example.domain.eventService.contracts.AcceptRequestOnInvitation
import com.example.domain.eventService.contracts.CancelRequestOnInvitation
import com.example.domain.eventService.contracts.RejectMemberOnInvitation
import com.example.domain.eventService.models.Member
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment.AcceptRequestOnInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment.CancelRequestOnInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment.RejectMemberOnInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.memberUseCases.GetAllMemberByAuthorUseCase
import com.example.ngkafisha.application.eventService.useCases.memberUseCases.GetAllMemberByStudentUseCase
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getAllMemberByStudentUseCase: GetAllMemberByStudentUseCase,
    private val getAllMemberByPublisherUseCase: GetAllMemberByAuthorUseCase,
    private val cancelRequestOnInvitationUseCase: CancelRequestOnInvitationUseCase,
    private val rejectMemberOnInvitationUseCase: RejectMemberOnInvitationUseCase,
    private val acceptRequestOnInvitationUseCase: AcceptRequestOnInvitationUseCase,
    val sessionInfoStore: SessionInfoStore
) : ViewModel() {

    private val _state = MutableStateFlow<ActualState>(ActualState.Initialized)
    val state: StateFlow<ActualState> = _state.asStateFlow()

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members.asStateFlow()

    private val _student = mutableStateOf<Student?>(null)
    val student: Student? get() = _student.value

    fun reactionOnError(errorMessage: String){

    }

    fun cancelRequest(invId: UUID, eventId: UUID){

        viewModelScope.launch {

            val response = cancelRequestOnInvitationUseCase(CancelRequestOnInvitationUseCase.Request(
                CancelRequestOnInvitation(
                    invId,
                    eventId
                )
            ))

            if(!response.isSuccess){
                reactionOnError(response.errorMessage)
                return@launch
            }

            refresh()
        }

    }

    fun acceptRequest(invId: UUID, eventId: UUID, studentId: UUID){

        viewModelScope.launch {

            val response = acceptRequestOnInvitationUseCase(AcceptRequestOnInvitationUseCase.Request(
                AcceptRequestOnInvitation(
                    invId,
                    eventId,
                    studentId
                )
            ))

            if(!response.isSuccess){
                reactionOnError(response.errorMessage)
                return@launch
            }

            refresh()
        }

    }

    fun rejectRequest(invId: UUID, eventId: UUID, studentId: UUID){

        viewModelScope.launch {

            val response = rejectMemberOnInvitationUseCase(RejectMemberOnInvitationUseCase.Request(
                RejectMemberOnInvitation(
                    invId,
                    eventId,
                    studentId
                )
            ))

            if(!response.isSuccess){
                reactionOnError(response.errorMessage)
                return@launch
            }

            refresh()
        }

    }

    fun refresh(){
        loadInvitations()
    }

    fun loadInvitations() {
        _state.value = ActualState.Loading

        viewModelScope.launch {

            val role = sessionInfoStore.currentAccount?.accountRole

            val result = when (role) {

                Role.Publisher -> {
                    getAllMemberByPublisherUseCase(
                        GetAllMemberByAuthorUseCase.Request()
                    )
                }

                Role.User, null -> {
                    getAllMemberByStudentUseCase(
                        GetAllMemberByStudentUseCase.Request()
                    )
                }
            }

            if (!result.isSuccess) {
                _state.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _members.value = result.value!!
            _state.value = ActualState.Success("")
        }
    }
}