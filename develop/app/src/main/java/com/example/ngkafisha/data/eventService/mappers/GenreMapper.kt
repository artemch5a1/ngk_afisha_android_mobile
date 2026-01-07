package com.example.ngkafisha.data.eventService.mappers

import com.example.domain.eventService.models.Genre
import com.example.ngkafisha.data.eventService.dto.events.GenreDto

object GenreMapper {

    fun toDomain(genreDto: GenreDto) : Genre
    {
        return Genre(
            genreId = genreDto.genreId,
            title = genreDto.title
        )
    }

    fun toListDomain(genreDtos: List<GenreDto>) : List<Genre>
    {
        return genreDtos.map { genreDto -> toDomain(genreDto) }
    }

}