package com.example.ngkafisha.application.identityService.accountContext.useCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    sessionStoreService: SessionStoreService
)
    : BaseUseCase<Unit, Unit>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<Unit> {

        sessionStoreService.resetSession()

        return CustomResult.success(Unit)
    }

}