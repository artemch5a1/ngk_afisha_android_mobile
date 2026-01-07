package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.models.EventType

interface EventTypeRepository {

    suspend fun getAllEventTypes() : List<EventType>

}