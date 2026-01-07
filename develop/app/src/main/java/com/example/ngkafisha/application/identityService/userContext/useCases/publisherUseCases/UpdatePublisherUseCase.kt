package com.example.ngkafisha.application.identityService.userContext.useCases.publisherUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.PublisherRepository
import com.example.domain.identityService.userContext.models.Publisher
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class UpdatePublisherUseCase @Inject constructor(
    private val publisherRepository: PublisherRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<UpdatePublisherUseCase.Request, UUID>(sessionStoreService) {
    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val response = publisherRepository.updatePublisher(request.publisher)

        return CustomResult.Companion.success(response)
    }


    data class Request(val publisher: Publisher)
}