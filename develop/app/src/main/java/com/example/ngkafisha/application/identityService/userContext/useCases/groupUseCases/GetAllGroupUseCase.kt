package com.example.ngkafisha.application.identityService.userContext.useCases.groupUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Group
import javax.inject.Inject

class GetAllGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<Group>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<Group>> {
        val result = groupRepository.getAllGroups()

        return CustomResult.success(result)
    }
}