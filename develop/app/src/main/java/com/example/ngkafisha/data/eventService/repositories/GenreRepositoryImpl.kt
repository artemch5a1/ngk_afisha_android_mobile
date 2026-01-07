package com.example.ngkafisha.data.eventService.repositories

import com.example.domain.eventService.abstractions.repositories.GenreRepository
import com.example.domain.eventService.models.Genre
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.GenreMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : GenreRepository {

    override suspend fun getAllGenres(): List<Genre> {

        return safeApiCall {

            val response = eventApi.getAllGenreAsync()

            GenreMapper.toListDomain(response)
        }

    }

}