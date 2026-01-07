package com.example.ngkafisha.data.eventService.dto.events

import java.util.UUID

data class UpdateEventDto(
    val eventId: UUID,
    val title: String,
    val shortDescription: String,
    val description: String,
    val dateStart: String,
    val locationId: Int,
    val genreId: Int,
    val typeId: Int,
    val minAge: Int
)
