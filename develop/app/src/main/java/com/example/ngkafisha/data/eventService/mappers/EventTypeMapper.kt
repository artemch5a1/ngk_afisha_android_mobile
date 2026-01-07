package com.example.ngkafisha.data.eventService.mappers

import com.example.domain.eventService.models.EventType
import com.example.ngkafisha.data.eventService.dto.events.EventTypeDto

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