package com.example.ngkafisha.application.eventService.useCases.invitationUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.models.Invitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class GetInvitationById @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetInvitationById.Request, Invitation>(sessionStoreService) {

    data class Request(val invitationId: UUID)

    override suspend fun invokeLogic(request: Request): CustomResult<Invitation> {
        val result = invitationRepository.getInvitationById(request.invitationId)

        return CustomResult.success(result)
    }

}