package com.example.ngkafisha.application.eventService.useCases.invitationUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.util.UUID
import javax.inject.Inject

class DeleteInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<DeleteInvitationUseCase.Request, UUID>(sessionStoreService) {

    data class Request(val eventId: UUID, val invitationId: UUID)

    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val result = invitationRepository.deleteInvitation(request.eventId, request.invitationId)

        return CustomResult.success(result)
    }

}