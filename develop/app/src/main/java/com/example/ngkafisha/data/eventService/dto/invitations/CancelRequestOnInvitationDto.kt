package com.example.ngkafisha.data.eventService.dto.invitations

import java.util.UUID

data class CancelRequestOnInvitationDto(
    val invitationId: UUID,
    val eventId: UUID
)
