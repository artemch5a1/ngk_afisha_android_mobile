package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.eventService.useCases.eventTypeUseCases.GetAllEventTypesUseCase
import com.example.application.eventService.useCases.eventUseCases.GetAllEventsByAuthorUseCase
import com.example.application.eventService.useCases.eventUseCases.GetAllEventsUseCase
import com.example.domain.common.enums.Role
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.models.Event
import com.example.domain.eventService.models.EventType
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.presentation.models.itemViewModels.EventTypeItemViewModel
import com.example.ngkafisha.presentation.models.states.ActualState
import com.example.ngkafisha.presentation.models.states.SelectedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEventScreenViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val getAllEventTypesUseCase: GetAllEventTypesUseCase,
    private val getAllEventsByAuthorUseCase: GetAllEventsByAuthorUseCase,
    val  sessionInfoStore: SessionInfoStore
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val actualState: StateFlow<ActualState> = _actualState.asStateFlow()

    private val _types = MutableLiveData<List<SelectedState<EventTypeItemViewModel>>>(emptyList())
    val types: LiveData<List<SelectedState<EventTypeItemViewModel>>> = _types

    private val _events = MutableLiveData<List<Event>>(emptyList())
    val events: LiveData<List<Event>> get() = _events

    private var eventData = MutableLiveData<List<Event>>(emptyList())

    private val _textSearch = MutableLiveData("")

    val textSearch:LiveData<String> get() = _textSearch

    fun updateTextSearch(textSearch: String){
        _textSearch.value = textSearch
    }

    fun refresh() {
        loadEvents()
        loadTypes()
        _textSearch.value = ""
    }

    init {
        refresh()
    }

    fun loadEvents(){
        _actualState.value = ActualState.Loading

        viewModelScope.launch {
            val result: CustomResult<List<Event>> = getAllEvent()

            if(!result.isSuccess){
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            _events.value = result.value!!
            eventData.value = result.value
            _actualState.value = ActualState.Initialized
        }
    }

    private suspend fun getAllEvent() : CustomResult<List<Event>> {
        if(sessionInfoStore.currentAccount?.accountRole == Role.Publisher){
            return getAllEventsByAuthorUseCase(GetAllEventsByAuthorUseCase.Request())
        }

        return getAllEventsUseCase(GetAllEventsUseCase.Request())
    }

    fun loadTypes() {
        _actualState.value = ActualState.Loading

        _types.value?.forEach { it.isSelected = false }

        viewModelScope.launch {
            val result: CustomResult<List<EventType>> = getAllEventTypesUseCase(Unit)

            if (!result.isSuccess) {
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            val eventVM: List<EventTypeItemViewModel> =
                result.value!!.map { eventType -> EventTypeItemViewModel(eventType) }

            val currentSelected = _types.value?.filter { it.isSelected }?.map { it.item.eventType.typeId } ?: emptyList()

            _types.value = eventVM.map { vm ->
                SelectedState(vm, currentSelected.contains(vm.eventType.typeId))
            }

            _actualState.value = ActualState.Initialized
        }
    }

    fun filterEvents(){
        _events.value = filterBySearchText(
            textSearch.value ?: "",
            filterBySelectedType(eventData.value!!))
    }

    private fun filterBySelectedType(events: List<Event>) : List<Event> {

        if(!(_types.value!!.any { y -> y.isSelected }))
            return events

        return events.filter { x ->
            _types.value!!.any { y -> y.isSelected and (y.item.eventType.typeId == x.typeId)}
        }

    }

    private fun filterBySearchText(searchText:String, events: List<Event>) : List<Event>
    {
        return events.filter { x ->
            x.title.contains(searchText) || x.shortDescription.contains(searchText)
        }
    }
}