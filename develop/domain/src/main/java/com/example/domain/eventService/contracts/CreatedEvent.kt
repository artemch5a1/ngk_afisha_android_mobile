package com.example.domain.eventService.contracts

import java.util.UUID

data class CreatedEvent(
    val eventId: UUID,
    val title: String,
    val shortDescription: String,
    val description: String,
    val dateStart: String,
    val locationId: Int,
    val genreId: Int,
    val typeId: Int,
    val minAge: Int,
    val uploadUrl: String
)
