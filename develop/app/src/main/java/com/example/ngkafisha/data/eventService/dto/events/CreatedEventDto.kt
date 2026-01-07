package com.example.ngkafisha.data.eventService.dto.events

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreatedEventDto(
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
