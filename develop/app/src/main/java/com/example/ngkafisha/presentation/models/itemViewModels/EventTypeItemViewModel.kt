package com.example.ngkafisha.presentation.models.itemViewModels

import com.example.domain.eventService.models.EventType
import com.example.ngkafisha.presentation.models.interfaces.IHaveTitle

data class EventTypeItemViewModel(val eventType: EventType)
    : IHaveTitle{

    override val title: String get() = eventType.title

}