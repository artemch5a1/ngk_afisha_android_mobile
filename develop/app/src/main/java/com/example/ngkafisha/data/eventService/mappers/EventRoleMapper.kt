package com.example.ngkafisha.data.eventService.mappers

import com.example.domain.eventService.models.EventRole
import com.example.ngkafisha.data.eventService.dto.invitations.EventRoleDto

object EventRoleMapper {

    fun toDomain(eventRoleDto: EventRoleDto) : EventRole {

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