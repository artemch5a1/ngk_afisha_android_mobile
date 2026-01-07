package com.example.domain.eventService.contracts

import java.util.UUID

data class UpdatedEvent(
    val eventId: UUID,
    val uploadUrl: String
)
