package com.example.application.eventService.useCases.eventRoleUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.EventRoleRepository
import com.example.domain.eventService.models.EventRole
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllEventRoleUseCase @Inject constructor(
    private val eventRoleRepository: EventRoleRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<EventRole>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<EventRole>> {
        val result = eventRoleRepository.getAllEventRole()

        return CustomResult.success(result)
    }

}