package com.example.data.eventService.mappers

import com.example.data.eventService.dto.invitations.EventRoleDto
import com.example.domain.eventService.models.EventRole
import kotlin.collections.map

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