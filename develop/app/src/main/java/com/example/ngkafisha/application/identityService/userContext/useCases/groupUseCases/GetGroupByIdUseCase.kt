package com.example.ngkafisha.application.identityService.userContext.useCases.groupUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.domain.identityService.userContext.models.Group
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class GetGroupByIdUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<GetGroupByIdUseCase.Request, Group>(sessionStoreService) {
    data class Request(val groupId:Int)

    override suspend fun invokeLogic(request: Request): CustomResult<Group> {
        val result = groupRepository.getGroupById(request.groupId)

        return CustomResult.success(result)
    }
}