package com.example.application.eventService.useCases.locationUseCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.eventService.abstractions.repositories.LocationRepository
import com.example.domain.eventService.models.Location
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class GetAllLocation @Inject constructor(
    private val locationRepository: LocationRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, List<Location>>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<List<Location>> {
        val result = locationRepository.getAllLocation()

        return CustomResult.success(result)
    }

}