package com.example.ngkafisha.application.eventService.useCases.invitationUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.ngkafisha.domain.eventService.models.Invitation
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import java.util.UUID
import javax.inject.Inject

class GetAllInvitationByEventUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllInvitationByEventUseCase.Request, List<Invitation>>(sessionStoreService) {

    data class Request(val eventId: UUID, val skip:Int = 0, val take:Int = Int.MAX_VALUE)

    override suspend fun invokeLogic(request: Request): CustomResult<List<Invitation>> {
        val result = invitationRepository.getAllInvitationByEvent(request.eventId, request.skip, request.take)

        return CustomResult.Companion.success(result)
    }

}