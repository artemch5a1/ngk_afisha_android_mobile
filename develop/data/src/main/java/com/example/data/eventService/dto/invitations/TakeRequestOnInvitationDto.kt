package com.example.data.eventService.dto.invitations

import java.util.UUID

data class TakeRequestOnInvitationDto(
    val invitationId: UUID,
    val eventId: UUID
)
