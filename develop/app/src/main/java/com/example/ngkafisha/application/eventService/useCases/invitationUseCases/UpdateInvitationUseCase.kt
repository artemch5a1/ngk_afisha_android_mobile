package com.example.ngkafisha.application.eventService.useCases.invitationUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.models.Invitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class UpdateInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<UpdateInvitationUseCase.Request, UUID>(sessionStoreService) {

    data class Request(val invitation: Invitation)

    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val result = invitationRepository.updateInvitation(request.invitation)

        return CustomResult.success(result)
    }

}