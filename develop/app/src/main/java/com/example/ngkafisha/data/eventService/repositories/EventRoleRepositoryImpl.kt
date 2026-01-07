package com.example.ngkafisha.data.eventService.repositories

import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.EventRoleMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRoleRepository
import com.example.ngkafisha.domain.eventService.models.EventRole
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