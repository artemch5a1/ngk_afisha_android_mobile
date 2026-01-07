package com.example.domain.eventService.abstractions.repositories

import com.example.domain.eventService.models.Location


interface LocationRepository {

    suspend fun getAllLocation(): List<Location>

}