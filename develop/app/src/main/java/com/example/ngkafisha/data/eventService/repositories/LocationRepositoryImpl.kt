package com.example.ngkafisha.data.eventService.repositories

import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.LocationMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import com.example.ngkafisha.domain.eventService.abstractions.repositories.LocationRepository
import com.example.ngkafisha.domain.eventService.models.Location
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