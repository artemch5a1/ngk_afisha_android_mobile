package com.example.ngkafisha.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.models.Event
import com.example.domain.eventService.models.EventType
import com.example.domain.eventService.models.Genre
import com.example.domain.eventService.models.Location
import com.example.ngkafisha.application.eventService.useCases.eventTypeUseCases.GetAllEventTypesUseCase
import com.example.ngkafisha.application.eventService.useCases.eventUseCases.CreateEventUseCase
import com.example.ngkafisha.application.eventService.useCases.eventUseCases.GetEventByIdUseCase
import com.example.ngkafisha.application.eventService.useCases.eventUseCases.UpdateEventUseCase
import com.example.ngkafisha.application.eventService.useCases.genreUseCases.GetAllGenres
import com.example.ngkafisha.application.eventService.useCases.locationUseCases.GetAllLocation
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateOrEditEventViewModel @Inject constructor(
    private val createEventUseCase: CreateEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val getAllGenres: GetAllGenres,
    private val  getAllLocation: GetAllLocation,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val getAllEventTypesUseCase: GetAllEventTypesUseCase,
) : ViewModel() {

    private var isEditMode: Boolean = false

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState: StateFlow<ActualState> get() = _actualState.asStateFlow()

    private val _eventState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val eventState: StateFlow<ActualState> get() = _eventState.asStateFlow()

    private val _types = MutableLiveData<List<EventType>>()
    val types: LiveData<List<EventType>> get() = _types

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations


    private val _eventCard = mutableStateOf<Event?>(null)
    val eventCard: Event? get() = _eventCard.value

    private val _textAction = MutableLiveData("Создать событие")

    val textAction:LiveData<String> get() = _textAction

    private val _textPage = MutableLiveData("Создание события")

    val textPage:LiveData<String> get() = _textPage


    fun executeAction(imageFile: File?){
        if(isEditMode){
            updateEvent(imageFile)
            return
        }
        else {
            createEvent(imageFile)
        }
    }

    fun updateEvent(newState: Event){
        _eventCard.value = newState
    }

    private fun createEvent(imageFile: File?) {
        val currentEvent = eventCard ?: return

        _eventState.value = ActualState.Loading

        viewModelScope.launch {
            val result = createEventUseCase(
                CreateEventUseCase.Request(
                    currentEvent,
                    imageFile
                )
            )

            if (!result.isSuccess) {
                _eventState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _eventState.value = ActualState.Success("")
        }
    }

    private fun updateEvent(imageFile: File?) {
        val currentEvent = eventCard ?: return

        _eventState.value = ActualState.Loading

        viewModelScope.launch {
            val result = updateEventUseCase(
                UpdateEventUseCase.Request(
                currentEvent,
                imageFile
                ))

            if (!result.isSuccess) {
                _eventState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _eventState.value = ActualState.Success("")
        }
    }

    fun loadEvent(eventId: String?){

        _actualState.value = ActualState.Loading

        if(eventId == null){
            _eventCard.value = Event(
                eventId = UUID.randomUUID(),
                title = "",
                description = "",
                shortDescription = "",
                dateStart = LocalDateTime.now(),
                locationId = 0,
                genreId = 0,
                typeId = 0,
                minAge = 14,
                downloadUrl = ""
            )
            viewModelScope.launch {
                loadCatalogs()
            }
            _actualState.value = ActualState.Initialized
            return
        }

        val id: UUID = UUID.fromString(eventId)

        viewModelScope.launch {
            isEditMode = true

            loadCatalogs()

            _textAction.value = "Изменить событие"

            _textPage.value = "Обновление события"

            val result: CustomResult<Event> = getEventByIdUseCase(
                GetEventByIdUseCase.Request(id)
            )

            if(!result.isSuccess){
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _eventCard.value = result.value
            _actualState.value = ActualState.Initialized
        }
    }

    suspend fun loadCatalogs(){
        loadTypes()
        loadLocations()
        loadGenres()
    }

    suspend fun loadTypes() : CustomResult<Unit> {
        val result: CustomResult<List<EventType>> = getAllEventTypesUseCase(Unit)

        if (!result.isSuccess) {
            return CustomResult.Companion.failure(result.errorMessage)
        }

        _types.value = result.value!!

        return CustomResult.success(Unit)
    }

    suspend fun loadLocations() : CustomResult<Unit> {
        val result: CustomResult<List<Location>> = getAllLocation(Unit)

        if (!result.isSuccess) {
            return CustomResult.Companion.failure(result.errorMessage)
        }

        _locations.value = result.value!!

        return CustomResult.success(Unit)
    }

    suspend fun loadGenres() : CustomResult<Unit> {

        val result: CustomResult<List<Genre>> = getAllGenres(Unit)

        if (!result.isSuccess) {
            return CustomResult.Companion.failure(result.errorMessage)
        }

        _genres.value = result.value!!

        return CustomResult.success(Unit)
    }

}