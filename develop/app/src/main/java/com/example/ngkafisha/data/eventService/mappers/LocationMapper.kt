package com.example.ngkafisha.data.eventService.mappers

import com.example.ngkafisha.data.eventService.dto.events.LocationDto
import com.example.ngkafisha.domain.eventService.models.Location

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