package com.example.application.eventService.useCases.invitationUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.models.Invitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class CreateInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<CreateInvitationUseCase.Request, Unit>(sessionStoreService) {

    data class Request(val invitation: Invitation)

    override suspend fun invokeLogic(request: Request): CustomResult<Unit> {
        val result = invitationRepository.createInvitation(request.invitation)

        return CustomResult.success(result)
    }

}