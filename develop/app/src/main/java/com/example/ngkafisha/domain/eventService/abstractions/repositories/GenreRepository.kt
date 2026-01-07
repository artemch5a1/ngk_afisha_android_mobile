package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.models.Genre

interface GenreRepository {

    suspend fun getAllGenres() : List<Genre>

}