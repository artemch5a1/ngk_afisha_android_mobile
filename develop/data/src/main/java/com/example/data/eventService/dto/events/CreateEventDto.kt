package com.example.data.eventService.dto.events

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventDto(
    val title: String,
    val shortDescription: String,
    val description: String,
    val dateStart: String,
    val locationId: Int,
    val genreId: Int,
    val typeId: Int,
    val minAge: Int
)
