package com.example.domain.eventService.abstractions.repositories

import com.example.domain.eventService.models.Genre


interface GenreRepository {

    suspend fun getAllGenres() : List<Genre>

}