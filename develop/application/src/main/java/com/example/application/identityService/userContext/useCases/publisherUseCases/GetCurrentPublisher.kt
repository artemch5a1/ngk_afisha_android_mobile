package com.example.application.identityService.userContext.useCases.publisherUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.PublisherRepository
import com.example.domain.identityService.userContext.models.Publisher
import javax.inject.Inject

class GetCurrentPublisher @Inject constructor(
    private val publisherRepository: PublisherRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, Publisher>(sessionStoreService) {


    override suspend fun invokeLogic(request: Unit): CustomResult<Publisher> {
        val response = publisherRepository.getCurrentPublisher()

        return CustomResult.success(response)
    }


}