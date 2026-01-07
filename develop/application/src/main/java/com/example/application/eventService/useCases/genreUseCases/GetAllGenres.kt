package com.example.application.eventService.useCases.genreUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.GenreRepository
import com.example.domain.eventService.models.Genre
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllGenres @Inject constructor(
    private val genreRepository: GenreRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<Genre>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<Genre>> {
        val result = genreRepository.getAllGenres()

        return CustomResult.success(result)
    }

}