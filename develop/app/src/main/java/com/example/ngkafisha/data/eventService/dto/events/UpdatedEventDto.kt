package com.example.ngkafisha.data.eventService.dto.events

import java.util.UUID

data class UpdatedEventDto(
    val eventId: UUID,
    val uploadUrl: String
)
