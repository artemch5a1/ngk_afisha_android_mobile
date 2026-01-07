package com.example.data.eventService.dto.events

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val genreId: Int,
    val title: String
)