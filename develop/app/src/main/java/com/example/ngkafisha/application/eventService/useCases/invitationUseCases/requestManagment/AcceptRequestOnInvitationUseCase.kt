package com.example.ngkafisha.application.eventService.useCases.invitationUseCases.requestManagment

import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.contracts.AcceptRequestOnInvitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class AcceptRequestOnInvitationUseCase @Inject constructor(
    sessionStoreService: SessionStoreService,
    private val invitationRepository: InvitationRepository
) : BaseUseCase<AcceptRequestOnInvitationUseCase.Request, UUID>(sessionStoreService)  {


    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val response =
            invitationRepository.acceptRequestOnInvitation(request.acceptRequestOnInvitation)

        return CustomResult.Companion.success(response)
    }

    data class Request(val acceptRequestOnInvitation: AcceptRequestOnInvitation);

}