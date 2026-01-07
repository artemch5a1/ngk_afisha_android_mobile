package com.example.ngkafisha.data.eventService.repositories

import com.example.domain.eventService.abstractions.repositories.LocationRepository
import com.example.domain.eventService.models.Location
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.LocationMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
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