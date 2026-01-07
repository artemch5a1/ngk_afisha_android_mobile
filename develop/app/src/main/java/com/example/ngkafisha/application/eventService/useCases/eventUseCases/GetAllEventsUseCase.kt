package com.example.ngkafisha.application.eventService.useCases.eventUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRepository
import com.example.domain.eventService.models.Event
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllEventsUseCase.Request, List<Event>>(sessionStoreService) {

    data class Request(val skip:Int = 0, val take:Int = Int.MAX_VALUE)

    override suspend fun invokeLogic(request: Request): CustomResult<List<Event>> {
        val result = eventRepository.getAllEvents(request.skip, request.take)

        return CustomResult.Companion.success(result)
    }

}