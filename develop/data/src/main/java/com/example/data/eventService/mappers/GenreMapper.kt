package com.example.data.eventService.mappers

import com.example.data.eventService.dto.events.GenreDto
import com.example.domain.eventService.models.Genre
import kotlin.collections.map

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