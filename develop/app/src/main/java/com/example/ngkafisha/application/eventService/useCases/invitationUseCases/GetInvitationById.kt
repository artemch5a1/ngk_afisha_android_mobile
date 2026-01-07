package com.example.ngkafisha.application.eventService.useCases.invitationUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.ngkafisha.domain.eventService.models.Invitation
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
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