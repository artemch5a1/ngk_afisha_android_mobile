package com.example.application.eventService.useCases.invitationUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.models.Invitation
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllInvitationByAuthorUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetAllInvitationByAuthorUseCase.Request, List<Invitation>>(sessionStoreService) {

    data class Request(val skip:Int = 0, val take:Int = Int.MAX_VALUE)

    override suspend fun invokeLogic(request: Request): CustomResult<List<Invitation>> {
        val result = invitationRepository.getAllInvitationByAuthor(request.skip, request.take)

        return CustomResult.Companion.success(result)
    }

}