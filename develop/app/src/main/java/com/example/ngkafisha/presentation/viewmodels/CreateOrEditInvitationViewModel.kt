package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.CreateInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.UpdateInvitationUseCase
import com.example.ngkafisha.application.eventService.useCases.eventRoleUseCases.GetAllEventRoleUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.GetInvitationById
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.models.EventRole
import com.example.ngkafisha.domain.eventService.models.Invitation
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateOrEditInvitationViewModel @Inject constructor(
    private val getInvitationById: GetInvitationById,
    private val createInvitationUseCase: CreateInvitationUseCase,
    private val updateInvitationUseCase: UpdateInvitationUseCase,
    private val getAllEventRoleUseCase: GetAllEventRoleUseCase
) : ViewModel() {

    private var isEditMode = false

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState: StateFlow<ActualState> get() = _actualState.asStateFlow()

    private val _invitationState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val invitationState: StateFlow<ActualState> get() = _invitationState.asStateFlow()

    private val _roles = MutableLiveData<List<EventRole>>()
    val roles: LiveData<List<EventRole>> get() = _roles

    private val _invitation = mutableStateOf<Invitation?>(null)
    val invitation: Invitation? get() = _invitation.value

    private val _textAction = MutableLiveData("Создать приглашение")
    val textAction: LiveData<String> get() = _textAction

    private val _textPage = MutableLiveData("Создание приглашения")
    val textPage: LiveData<String> get() = _textPage

    fun loadInvitation(eventId: String, invitationId: String?) {
        _actualState.value = ActualState.Loading
        val eventUUID = UUID.fromString(eventId)

        viewModelScope.launch {
            loadRoles()

            if (invitationId == null) {
                _invitation.value = Invitation(
                    invitationId = UUID.randomUUID(),
                    eventId = eventUUID,
                    roleId = 0,
                    shortDescription = "",
                    description = "",
                    requiredMember = 1,
                    acceptedMember = 0,
                    deadLine = LocalDateTime.now()
                )
                _actualState.value = ActualState.Initialized
            } else {
                isEditMode = true
                _textAction.value = "Изменить приглашение"
                _textPage.value = "Обновление приглашения"

                val result: CustomResult<Invitation> = getInvitationById(GetInvitationById.Request(UUID.fromString(invitationId)))

                if (!result.isSuccess) {
                    _actualState.value = ActualState.Error(result.errorMessage)
                    return@launch
                }
                _invitation.value = result.value
                _actualState.value = ActualState.Initialized
            }
        }
    }

    suspend fun loadRoles() {
        val result = getAllEventRoleUseCase(Unit)  // вызываем UseCase через скобки
        if (!result.isSuccess) {
            _actualState.value = ActualState.Error(result.errorMessage)
            return
        }
        _roles.value = result.value!!
    }

    fun updateInvitation(newInvitation: Invitation) {
        _invitation.value = newInvitation
    }

    fun executeAction() {
        val current = invitation ?: return
        _invitationState.value = ActualState.Loading

        viewModelScope.launch {
            val result = if (isEditMode) {
                updateInvitationUseCase(UpdateInvitationUseCase.Request(current))
            } else {
                createInvitationUseCase(CreateInvitationUseCase.Request(current))
            }

            if (!result.isSuccess) {
                _invitationState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _invitationState.value = ActualState.Success("")
        }
    }
}