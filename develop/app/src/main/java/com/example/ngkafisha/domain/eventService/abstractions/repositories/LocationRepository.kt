package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.models.Location

interface LocationRepository {

    suspend fun getAllLocation(): List<Location>

}