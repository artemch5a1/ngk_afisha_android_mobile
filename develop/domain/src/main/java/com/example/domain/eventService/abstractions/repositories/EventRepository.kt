package com.example.domain.eventService.abstractions.repositories


import com.example.domain.eventService.contracts.CreatedEvent
import com.example.domain.eventService.contracts.UpdatedEvent
import com.example.domain.eventService.models.Event
import java.util.UUID

interface EventRepository {

    suspend fun getAllEvents(skip:Int = 0, take:Int = 50): List<Event>

    suspend fun getAllEventsByAuthor(
        skip: Int,
        take: Int
    ): List<Event>

    suspend fun getEventById(eventId: UUID): Event

    suspend fun createEvent(event: Event): CreatedEvent

    suspend fun updateEvent(event: Event): UpdatedEvent

    suspend fun deleteEvent(eventId: UUID): UUID
}