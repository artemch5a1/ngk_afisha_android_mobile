package com.example.application.eventService.useCases.eventTypeUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventTypeRepository
import com.example.domain.eventService.models.EventType
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllEventTypesUseCase @Inject constructor(
    private val eventTypeRepository: EventTypeRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<EventType>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<EventType>> {
        val result = eventTypeRepository.getAllEventTypes()

        return CustomResult.Companion.success(result)
    }

}