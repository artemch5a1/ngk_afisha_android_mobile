package com.example.ngkafisha.application.eventService.useCases.eventRoleUseCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRoleRepository
import com.example.ngkafisha.domain.eventService.models.EventRole
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
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