package com.example.ngkafisha.data.eventService.mappers

import com.example.domain.eventService.models.Location
import com.example.ngkafisha.data.eventService.dto.events.LocationDto

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