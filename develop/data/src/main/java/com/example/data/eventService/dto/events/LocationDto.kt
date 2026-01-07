package com.example.data.eventService.dto.events

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val locationId: Int,
    val title: String,
    val address: String
)
