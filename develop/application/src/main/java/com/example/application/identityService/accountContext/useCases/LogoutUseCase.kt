package com.example.application.identityService.accountContext.useCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    sessionStoreService: SessionStoreService
)
    : BaseUseCase<Unit, Unit>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<Unit> {

        // Очищаем все данные сессии (токен, email, password)
        sessionStoreService.clearAllSessionData()

        return CustomResult.success(Unit)
    }

}