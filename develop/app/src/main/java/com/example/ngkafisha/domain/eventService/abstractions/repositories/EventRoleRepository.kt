package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.models.EventRole

interface EventRoleRepository {

    suspend fun getAllEventRole(): List<EventRole>

}