package com.example.ngkafisha.presentation.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.enums.Role
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.models.Invitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.GetAllInvitationByAuthorUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.GetAllInvitationByEventUseCase
import com.example.ngkafisha.application.eventService.useCases.invitationUseCases.GetAllInvitationUseCase
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ListInvitationScreenViewModel @Inject constructor(
    private val getAllInvitationUseCase: GetAllInvitationUseCase,
    private val getAllInvitationByAuthorUseCase: GetAllInvitationByAuthorUseCase,
    private val getAllInvitationByEventUseCase: GetAllInvitationByEventUseCase,
    val sessionInfoStore: SessionInfoStore
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val actualState: StateFlow<ActualState> = _actualState.asStateFlow()

    private val _invitations = MutableLiveData<List<Invitation>>(emptyList())
    val invitations: LiveData<List<Invitation>> = _invitations

    private val originInvitations = MutableLiveData<List<Invitation>>(emptyList())

    private val _textSearch = MutableLiveData("")
    val textSearch: LiveData<String> get() = _textSearch

    private val _isEventMode = MutableLiveData(false)
    val isEventMode: LiveData<Boolean> get() = _isEventMode

    private val _changed = MutableLiveData(false)
    val changed: LiveData<Boolean> get() = _changed

    fun updateTextSearch(text: String) {
        _textSearch.value = text
        filterInvitations()
    }

    private val _eventSearchText = MutableLiveData("")
    val eventSearchText: LiveData<String> get() = _eventSearchText

    fun updateEventSearch(text: String) {
        _eventSearchText.value = text
        filterInvitations()
    }

    fun refresh() {

        if(_isEventMode.value == true){
            _changed.value = true
            return
        }

        _textSearch.value = ""
        _eventSearchText.value = ""
        loadInvitations()
    }

    fun loadByEvent(eventId: String){

        _actualState.value = ActualState.Loading
        _isEventMode.value = true

        val eventIdUuid: UUID = UUID.fromString(eventId)

        viewModelScope.launch {
            val result: CustomResult<List<Invitation>> =
                getAllInvitationByEventUseCase(GetAllInvitationByEventUseCase.Request(eventIdUuid))

            if (!result.isSuccess) {
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            val list: List<Invitation> = result.value ?: emptyList()

            originInvitations.value = list
            _invitations.value = list

            _eventSearchText.value = list.firstOrNull()?.event?.title ?: ""
            _textSearch.value = ""

            _actualState.value = ActualState.Initialized
        }
    }

    private fun loadInvitations() {
        _actualState.value = ActualState.Loading

        viewModelScope.launch {
            val result: CustomResult<List<Invitation>> =
                invitationsLoader()

            if (!result.isSuccess) {
                _actualState.value = ActualState.Error(result.errorMessage)
                return@launch
            }

            val list = result.value ?: emptyList()

            originInvitations.value = list
            _invitations.value = list

            _actualState.value = ActualState.Initialized
        }
    }

    private suspend fun invitationsLoader() : CustomResult<List<Invitation>> {

        if(sessionInfoStore.isAuth && sessionInfoStore.currentAccount?.accountRole == Role.Publisher){
            return getAllInvitationByAuthorUseCase(GetAllInvitationByAuthorUseCase.Request())
        }
        else{
            return  getAllInvitationUseCase(GetAllInvitationUseCase.Request())
        }
    }

    fun filterInvitations() {
        val text = textSearch.value ?: ""
        val list = originInvitations.value ?: emptyList()

        _invitations.value = filterInvitationsByEvent(filterBySearchText(text, list))
    }

    private fun filterInvitationsByEvent(items: List<Invitation>) : List<Invitation> {
        val text = eventSearchText.value ?: ""

        val returnedData: List<Invitation>  = if (text.isBlank()) items
        else items.filter { it.event?.title?.contains(text, ignoreCase = true) ?: false }

        return returnedData
    }

    private fun filterBySearchText(search: String, items: List<Invitation>): List<Invitation> {
        if (search.isBlank()) return items

        return items.filter { inv ->
            inv.shortDescription.contains(search, ignoreCase = true) ||
                    (inv.event?.title?.contains(search, ignoreCase = true) ?: false)
        }
    }
}
