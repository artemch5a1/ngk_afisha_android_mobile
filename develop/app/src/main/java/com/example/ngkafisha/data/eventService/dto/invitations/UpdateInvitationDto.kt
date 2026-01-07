package com.example.ngkafisha.data.eventService.dto.invitations

import java.util.UUID

data class UpdateInvitationDto(
    val invitationId: UUID,
    val eventId: UUID,
    val roleId: Int,
    val shortDescription: String,
    val description: String,
    val requiredMember: Int,
    val deadLine: String
)
