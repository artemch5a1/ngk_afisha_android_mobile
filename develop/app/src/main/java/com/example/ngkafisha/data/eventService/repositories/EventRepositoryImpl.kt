package com.example.ngkafisha.data.eventService.repositories

import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.EventMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRepository
import com.example.ngkafisha.domain.eventService.contracts.CreatedEvent
import com.example.ngkafisha.domain.eventService.contracts.UpdatedEvent
import com.example.ngkafisha.domain.eventService.models.Event
import java.util.UUID
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : EventRepository {


    override suspend fun getAllEvents(
        skip: Int,
        take: Int
    ): List<Event> {

        return safeApiCall {
            val response = eventApi.getAllEventsAsync(skip, take)

            EventMapper.toListDomain(response)
        }

    }

    override suspend fun getEventById(eventId: UUID): Event {

        return safeApiCall {
            val response = eventApi.getEventByIdAsync(eventId)

            EventMapper.toDomain(response)
        }

    }

    override suspend fun createEvent(event: Event): CreatedEvent {
        return safeApiCall {
            val response = eventApi.createEventAsync(EventMapper.toCreateEventDto(event))

            EventMapper.toCreatedEvent(response)
        }
    }

    override suspend fun updateEvent(event: Event): UpdatedEvent {
        return safeApiCall {
            val response = eventApi.updateEventAsync(EventMapper.toUpdateEventDto(event))

            EventMapper.toUpdatedEvent(response)
        }
    }

    override suspend fun deleteEvent(eventId: UUID): UUID {
        return safeApiCall {
            eventApi.deleteEventAsync(eventId)
        }
    }

    override suspend fun getAllEventsByAuthor(
        skip: Int,
        take: Int
    ): List<Event> {

        return safeApiCall {
            val response = eventApi.getAllEventByAuthorAsync(skip, take)

            EventMapper.toListDomain(response)
        }

    }


}