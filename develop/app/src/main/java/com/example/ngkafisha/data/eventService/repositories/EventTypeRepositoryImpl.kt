package com.example.ngkafisha.data.eventService.repositories

import com.example.domain.eventService.abstractions.repositories.EventTypeRepository
import com.example.domain.eventService.models.EventType
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.EventTypeMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import javax.inject.Inject

class EventTypeRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : EventTypeRepository {

    override suspend fun getAllEventTypes(): List<EventType> {

        return safeApiCall {

            val response = eventApi.getAllEventTypeAsync()

            EventTypeMapper.toListDomain(response)
        }

    }

}