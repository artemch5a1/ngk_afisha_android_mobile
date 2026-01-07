package com.example.ngkafisha.data.eventService.mappers

import com.example.ngkafisha.data.eventService.dto.events.CreateEventDto
import com.example.ngkafisha.data.eventService.dto.events.CreatedEventDto
import com.example.ngkafisha.data.eventService.dto.events.EventDto
import com.example.ngkafisha.data.eventService.dto.events.UpdateEventDto
import com.example.ngkafisha.data.eventService.dto.events.UpdatedEventDto
import com.example.ngkafisha.domain.eventService.contracts.CreatedEvent
import com.example.ngkafisha.domain.eventService.contracts.UpdatedEvent
import com.example.ngkafisha.domain.eventService.models.Event
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId


object EventMapper {

    fun toDomain(eventDto: EventDto) : Event
    {
        return Event(
            eventId = eventDto.eventId,
            title = eventDto.title,
            shortDescription = eventDto.shortDescription,
            description = eventDto.description,
            dateStart = convertUtcStringToLocalDateTime(eventDto.dateStart),
            locationId = eventDto.locationId,
            location = eventDto.location?.let { LocationMapper.toDomain(it) },
            genreId = eventDto.genreId,
            genre = eventDto.genre?.let { GenreMapper.toDomain(it) },
            typeId = eventDto.typeId,
            type = eventDto.type?.let { EventTypeMapper.toDomain(it) },
            minAge = eventDto.minAge,
            downloadUrl = eventDto.downloadUrl
        )
    }

    fun toListDomain(eventTypeDtos: List<EventDto>) : List<Event>
    {
        return eventTypeDtos.map { eventTypeDto -> toDomain(eventTypeDto) }
    }

    fun toCreateEventDto(event: Event): CreateEventDto{

        return CreateEventDto(
            title = event.title,
            shortDescription = event.shortDescription,
            description = event.description,
            dateStart = event.dateStart.toUtcIso8601String(),
            locationId = event.locationId,
            genreId = event.genreId,
            typeId = event.typeId,
            minAge = event.minAge
        )

    }

    fun toUpdateEventDto(event: Event): UpdateEventDto{

        return UpdateEventDto(
            eventId = event.eventId,
            title = event.title,
            shortDescription = event.shortDescription,
            description = event.description,
            dateStart = event.dateStart.toUtcIso8601String(),
            locationId = event.locationId,
            genreId = event.genreId,
            typeId = event.typeId,
            minAge = event.minAge
        )

    }

    fun toCreatedEvent(createdEventDto: CreatedEventDto) : CreatedEvent{

        return CreatedEvent(
            eventId = createdEventDto.eventId,
            title = createdEventDto.title,
            shortDescription = createdEventDto.shortDescription,
            description = createdEventDto.description,
            dateStart = createdEventDto.dateStart,
            locationId = createdEventDto.locationId,
            genreId = createdEventDto.genreId,
            typeId = createdEventDto.typeId,
            minAge = createdEventDto.minAge,
            uploadUrl = createdEventDto.uploadUrl
        )

    }

    fun toUpdatedEvent(updatedEventDto: UpdatedEventDto) : UpdatedEvent{

        return UpdatedEvent(
            eventId = updatedEventDto.eventId,
            uploadUrl = updatedEventDto.uploadUrl
        )

    }


    private fun convertUtcStringToLocalDateTime(dateStr: String): LocalDateTime {

        val utcDateTime = OffsetDateTime.parse(dateStr)

        return utcDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }

    private fun LocalDateTime.toUtcIso8601String(): String {
        val zonedDateTime = this.atZone(ZoneId.systemDefault())

        val instant = zonedDateTime.toInstant()

        return instant.toString()
    }

}