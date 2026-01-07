package com.example.domain.eventService.models

import java.time.LocalDateTime
import java.util.UUID

data class Event(
    val eventId: UUID,
    val title: String,
    val shortDescription: String,
    val description: String,
    val dateStart: LocalDateTime,
    val locationId: Int,
    val location: Location? = null,
    val genreId: Int,
    val genre: Genre? = null,
    val typeId: Int,
    val type: EventType? = null,
    val minAge: Int,
    val downloadUrl: String
)