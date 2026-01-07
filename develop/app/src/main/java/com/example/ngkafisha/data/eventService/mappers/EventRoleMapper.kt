package com.example.ngkafisha.data.eventService.mappers

import com.example.ngkafisha.data.eventService.dto.invitations.EventRoleDto
import com.example.ngkafisha.domain.eventService.models.EventRole

object EventRoleMapper {

    fun toDomain(eventRoleDto: EventRoleDto) : EventRole{

        return EventRole(
            eventRoleId = eventRoleDto.eventRoleId,
            title = eventRoleDto.title,
            description = eventRoleDto.description
        )

    }

    fun toListDomain(eventRoleDtos: List<EventRoleDto>) : List<EventRole>{

        return eventRoleDtos.map { dto -> toDomain(dto) }

    }

}