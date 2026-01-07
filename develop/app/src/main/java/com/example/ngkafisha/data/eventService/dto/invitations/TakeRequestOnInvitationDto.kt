package com.example.ngkafisha.data.eventService.dto.invitations

import java.util.UUID

data class TakeRequestOnInvitationDto(
    val invitationId: UUID,
    val eventId: UUID
)
