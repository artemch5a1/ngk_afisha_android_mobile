package com.example.ngkafisha.data.eventService.dto.invitations

import java.util.UUID

data class AcceptRequestOnInvitationDto(
    val invitationId: UUID,
    val eventId: UUID,
    val studentId: UUID
)
