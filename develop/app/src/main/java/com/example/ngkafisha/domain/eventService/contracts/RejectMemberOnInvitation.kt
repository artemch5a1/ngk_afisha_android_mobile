package com.example.ngkafisha.domain.eventService.contracts

import java.util.UUID

data class RejectMemberOnInvitation(
    val invitationId: UUID,
    val eventId: UUID,
    val studentId: UUID
)
