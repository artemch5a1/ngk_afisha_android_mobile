package com.example.domain.eventService.contracts

import java.util.UUID

data class CancelRequestOnInvitation(
    val invitationId: UUID,
    val eventId: UUID
)
