package com.example.ngkafisha.application.eventService.useCases.eventUseCases

import com.example.domain.common.abstractions.utils.FileUploader
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRepository
import com.example.domain.eventService.contracts.UpdatedEvent
import com.example.domain.eventService.models.Event
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.io.File
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val fileUploader: FileUploader,
    sessionStoreService: SessionStoreService
) : BaseUseCase<UpdateEventUseCase.Request, UpdatedEvent>(sessionStoreService) {

    data class Request(val event: Event, val image: File?)

    override suspend fun invokeLogic(request: Request): CustomResult<UpdatedEvent> {
        val created = eventRepository.updateEvent(request.event)

        if(request.image == null)
            return CustomResult.success(created)

        fileUploader.uploadFile(created.uploadUrl, request.image)

        return CustomResult.success(created)
    }
}