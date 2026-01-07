package com.example.data.eventService.dto.invitations


import com.example.data.eventService.dto.events.EventDto
import java.util.UUID

data class InvitationDto(
    val invitationId: UUID,
    val eventId: UUID,
    val event: EventDto,
    val roleId: Int,
    val role: EventRoleDto,
    val shortDescription: String,
    val description: String,
    val requiredMember: Int,
    val acceptedMember: Int,
    val deadLine: String,
    val status: Int
)
