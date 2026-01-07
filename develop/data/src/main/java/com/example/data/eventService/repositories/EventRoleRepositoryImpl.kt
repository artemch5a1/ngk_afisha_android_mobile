package com.example.data.eventService.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.eventService.mappers.EventRoleMapper
import com.example.data.eventService.remote.EventApi
import com.example.domain.eventService.abstractions.repositories.EventRoleRepository
import com.example.domain.eventService.models.EventRole
import javax.inject.Inject

class EventRoleRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
): EventRoleRepository {
    override suspend fun getAllEventRole(): List<EventRole> {
        return safeApiCall {
            val response = eventApi.getAllEventRolesAsync()

            EventRoleMapper.toListDomain(response)
        }
    }
}