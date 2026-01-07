package com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.ngkafisha.domain.eventService.contracts.CancelRequestOnInvitation
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.util.UUID
import javax.inject.Inject

class CancelRequestOnInvitationUseCase @Inject constructor(
    sessionStoreService: SessionStoreService,
    private val invitationRepository: InvitationRepository
) : BaseUseCase<CancelRequestOnInvitationUseCase.Request, UUID>(sessionStoreService) {
    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val response =
            invitationRepository.cancelRequestOnInvitation(request.cancelRequestOnInvitation)

        return CustomResult.Companion.success(response)
    }


    data class Request(val cancelRequestOnInvitation: CancelRequestOnInvitation)

}