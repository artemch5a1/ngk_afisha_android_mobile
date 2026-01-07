package com.example.ngkafisha.application.eventService.useCases.eventUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRepository
import com.example.ngkafisha.domain.eventService.models.Event
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.util.UUID
import javax.inject.Inject

class GetAllEventsByAuthorUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllEventsByAuthorUseCase.Request, List<Event>>(sessionStoreService) {

    data class Request(val skip:Int = 0, val take:Int = Int.MAX_VALUE)

    override suspend fun invokeLogic(request: Request): CustomResult<List<Event>> {
        val result = eventRepository.getAllEventsByAuthor(request.skip, request.take)

        return CustomResult.Companion.success(result)
    }

}