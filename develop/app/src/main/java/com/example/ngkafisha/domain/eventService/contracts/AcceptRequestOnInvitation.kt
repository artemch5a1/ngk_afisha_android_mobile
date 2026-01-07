package com.example.ngkafisha.domain.eventService.contracts

import java.util.UUID

data class AcceptRequestOnInvitation(
    val invitationId: UUID,
    val eventId: UUID,
    val studentId: UUID
)
