package com.example.application.eventService.useCases.eventUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.abstractions.utils.FileUploader
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRepository
import com.example.domain.eventService.contracts.CreatedEvent
import com.example.domain.eventService.models.Event
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.io.File
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val fileUploader: FileUploader,
    sessionStoreService: SessionStoreService
) : BaseUseCase<CreateEventUseCase.Request, CreatedEvent>(sessionStoreService) {

    data class Request(val event: Event, val image: File?)

    override suspend fun invokeLogic(request: Request): CustomResult<CreatedEvent> {
        val created = eventRepository.createEvent(request.event)

        if(request.image == null)
            return CustomResult.success(created)

        fileUploader.uploadFile(created.uploadUrl, request.image)

        return CustomResult.success(created)
    }
}