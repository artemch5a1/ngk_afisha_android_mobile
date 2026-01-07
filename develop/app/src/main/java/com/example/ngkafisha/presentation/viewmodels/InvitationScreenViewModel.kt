package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.DeleteInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.GetInvitationById
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment.TakeRequestOnInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.memberUseCases.GetAllMemberByStudentUseCase
import com.example.ngkafisha.domain.common.enums.Role
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.contracts.TakeRequestOnInvitation
import com.example.ngkafisha.domain.eventService.models.Invitation
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InvitationScreenViewModel @Inject constructor(
    private val getInvitationById: GetInvitationById,
    private val deleteInvitationUseCase: DeleteInvitationUseCase,
    val sessionInfoStore: SessionInfoStore,
    private val getAllMemberByStudentUseCase: GetAllMemberByStudentUseCase,
    private val takeRequestOnInvitationUseCase: TakeRequestOnInvitationUseCase
) : ViewModel() {

    private var currentId: UUID? = null

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val actualState = _actualState.asStateFlow()

    private val _invitation = MutableStateFlow<Invitation?>(null)
    val invitation = _invitation.asStateFlow()

    private val _isAlreadyRequest = MutableStateFlow<Boolean>(false)
    val isAlreadyRequest = _isAlreadyRequest.asStateFlow()

    private val _deleteInvitationState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val deleteInvitationState: StateFlow<ActualState> = _deleteInvitationState.asStateFlow()


    fun refresh(){

        if(currentId != null)
            loadInvitation(currentId.toString())

    }

    fun takeRequest(){

        if(invitation.value == null)
            return

        viewModelScope.launch {
            val result = takeRequestOnInvitationUseCase(TakeRequestOnInvitationUseCase.Request(
                TakeRequestOnInvitation(
                    invitation.value!!.invitationId,
                    invitation.value!!.eventId,
                )
            ))

            if(!result.isSuccess){
                return@launch
            }

            refresh()
        }
    }

    fun loadInvitation(invitationId: String) {
        val uuid = try {
            UUID.fromString(invitationId)
        } catch (e: Exception) {
            _actualState.value = ActualState.Error("Неверный ID приглашения")
            return
        }

        currentId = uuid

        _actualState.value = ActualState.Loading

        viewModelScope.launch {
            val result = getInvitationById(GetInvitationById.Request(uuid))

            if (!result.isSuccess) {
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _invitation.value = result.value

            if(sessionInfoStore.currentAccount?.accountRole == Role.User){
                val resultMember = getAllMemberByStudentUseCase(GetAllMemberByStudentUseCase.Request())

                if(resultMember.isSuccess){
                    _isAlreadyRequest.value = resultMember.value!!.any { it ->
                        it.invitationId == invitation.value?.invitationId
                    }
                }
            }

            _actualState.value = ActualState.Success("loaded")
        }
    }

    fun resetDeleteInvitationState(){
        _deleteInvitationState.value = ActualState.Initialized
    }

    fun deleteInvitation(eventId: UUID, invitationId: UUID){

        _deleteInvitationState.value = ActualState.Loading

        viewModelScope.launch {
            val result: CustomResult<UUID> = deleteInvitationUseCase(
                DeleteInvitationUseCase.Request(
                    eventId,
                    invitationId
                )
            )

            if(!result.isSuccess){
                _deleteInvitationState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _deleteInvitationState.value = ActualState.Success("")

        }
    }

    fun resetState() {
        _actualState.value = ActualState.Initialized
    }
}