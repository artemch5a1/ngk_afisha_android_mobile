package com.example.data.eventService.dto.events

import kotlinx.serialization.Serializable

@Serializable
data class EventTypeDto(
    val typeId: Int,
    val title: String
)
