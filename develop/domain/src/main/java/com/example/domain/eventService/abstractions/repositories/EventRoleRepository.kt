package com.example.domain.eventService.abstractions.repositories

import com.example.domain.eventService.models.EventRole


interface EventRoleRepository {

    suspend fun getAllEventRole(): List<EventRole>

}