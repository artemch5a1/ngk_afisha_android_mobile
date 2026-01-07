package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.models.Event
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.application.eventService.useCases.eventUseCases.DeleteEventUseCase
import com.example.ngkafisha.application.eventService.useCases.eventUseCases.GetEventByIdUseCase
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    val  sessionInfoStore: SessionInfoStore
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState: StateFlow<ActualState> = _actualState.asStateFlow()

    private val _deleteEventState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val deleteEventState: StateFlow<ActualState> = _deleteEventState.asStateFlow()

    private val _event = mutableStateOf<Event?>(null)
    val event: Event? get() = _event.value

    fun deleteEvent(eventId: UUID){

        _deleteEventState.value = ActualState.Loading

        viewModelScope.launch {
            val result: CustomResult<UUID> = deleteEventUseCase(
                DeleteEventUseCase.Request(
                    eventId
                )
            )

            if(!result.isSuccess){
                _deleteEventState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _deleteEventState.value = ActualState.Success("")

        }
    }

    fun resetDeleteEventState(){
        _deleteEventState.value = ActualState.Initialized
    }

    fun loadEvent(eventId: String){

        _actualState.value = ActualState.Loading

        val eventIdUuid: UUID = UUID.fromString(eventId)

        viewModelScope.launch {
            val result: CustomResult<Event> = getEventByIdUseCase(
                GetEventByIdUseCase.Request(
                    eventIdUuid
                )
            )

            if(!result.isSuccess){
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _event.value = result.value
        }

        _actualState.value = ActualState.Initialized
    }
}