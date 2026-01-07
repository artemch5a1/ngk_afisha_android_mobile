package com.example.data.eventService.dto.invitations

import java.util.UUID

data class CreateInvitationDto(
    val eventId: UUID,
    val roleId: Int,
    val shortDescription: String,
    val description: String,
    val requiredMember: Int,
    val deadLine: String
)
