package com.example.ngkafisha.application.identityService.userContext.useCases.groupUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Group
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