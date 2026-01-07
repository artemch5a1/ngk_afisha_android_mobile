package com.example.data.eventService.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.eventService.mappers.EventTypeMapper
import com.example.data.eventService.remote.EventApi
import com.example.domain.eventService.abstractions.repositories.EventTypeRepository
import com.example.domain.eventService.models.EventType
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