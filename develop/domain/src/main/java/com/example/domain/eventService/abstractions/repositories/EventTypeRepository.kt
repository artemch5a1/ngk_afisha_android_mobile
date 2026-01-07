package com.example.domain.eventService.abstractions.repositories

import com.example.domain.eventService.models.EventType


interface EventTypeRepository {

    suspend fun getAllEventTypes() : List<EventType>

}