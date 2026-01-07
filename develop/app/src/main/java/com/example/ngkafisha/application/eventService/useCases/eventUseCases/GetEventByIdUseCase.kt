package com.example.ngkafisha.application.eventService.useCases.eventUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRepository
import com.example.domain.eventService.models.Event
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetEventByIdUseCase.Request, Event>(sessionStoreService) {

    data class Request(val eventId: UUID)

    override suspend fun invokeLogic(request: Request): CustomResult<Event> {
        val result = eventRepository.getEventById(request.eventId)

        return CustomResult.Companion.success(result)
    }

}