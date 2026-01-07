package com.example.data.eventService.dto.events

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class EventDto(
    val eventId: UUID,
    val title: String,
    val shortDescription: String,
    val description: String,
    val dateStart: String,
    val locationId: Int,
    val location: LocationDto? = null,
    val genreId: Int,
    val genre: GenreDto? = null,
    val typeId: Int,
    val type: EventTypeDto? = null,
    val minAge: Int,
    val downloadUrl: String
)
