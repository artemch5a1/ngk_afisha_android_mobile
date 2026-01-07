package com.example.ngkafisha.application.identityService.userContext.useCases.groupUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.domain.identityService.userContext.models.Group
import com.example.ngkafisha.application.common.base.BaseUseCase
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