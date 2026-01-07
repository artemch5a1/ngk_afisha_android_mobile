package com.example.data.eventService.mappers

import com.example.data.eventService.dto.events.EventTypeDto
import com.example.domain.eventService.models.EventType
import kotlin.collections.map

object EventTypeMapper {

    fun toDomain(eventTypeDto: EventTypeDto) : EventType
    {
        return EventType(
            typeId = eventTypeDto.typeId,
            title = eventTypeDto.title
        )
    }

    fun toListDomain(eventTypeDtos: List<EventTypeDto>) : List<EventType>
    {
        return eventTypeDtos.map { eventTypeDto -> toDomain(eventTypeDto) }
    }

}