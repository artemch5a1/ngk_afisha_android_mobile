package com.example.application.eventService.useCases.eventUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.util.UUID
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<DeleteEventUseCase.Request, UUID>(sessionStoreService) {

    data class Request(val eventId: UUID)

    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val deletedEventId:UUID = eventRepository.deleteEvent(request.eventId)

        return CustomResult.success(deletedEventId)
    }
}