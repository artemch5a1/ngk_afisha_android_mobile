package com.example.data.eventService.mappers

import com.example.data.eventService.dto.events.LocationDto
import com.example.domain.eventService.models.Location
import kotlin.collections.map

object LocationMapper {

    fun toDomain(locationDto: LocationDto) : Location
    {
        return Location(
            locationId = locationDto.locationId,
            title = locationDto.title,
            address = locationDto.address
        )
    }

    fun toListDomain(locationDtos: List<LocationDto>) : List<Location>
    {
        return locationDtos.map { locationDto -> toDomain(locationDto) }
    }

}