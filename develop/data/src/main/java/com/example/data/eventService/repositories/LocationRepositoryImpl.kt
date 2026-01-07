package com.example.data.eventService.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.eventService.mappers.LocationMapper
import com.example.data.eventService.remote.EventApi
import com.example.domain.eventService.abstractions.repositories.LocationRepository
import com.example.domain.eventService.models.Location
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : LocationRepository {

    override suspend fun getAllLocation(): List<Location> {

        return safeApiCall {

            val response = eventApi.getAllLocationAsync()

            LocationMapper.toListDomain(response)
        }

    }

}