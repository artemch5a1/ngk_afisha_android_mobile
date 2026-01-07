package com.example.ngkafisha.domain.eventService.contracts

import java.util.UUID

data class TakeRequestOnInvitation(
    val invitationId: UUID,
    val eventId: UUID
)
