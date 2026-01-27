package com.example.application.identityService.accountContext.useCases

import com.example.application.common.base.BaseUseCase
import com.example.application.identityService.accountContext.services.auth.Session
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    sessionStoreService: SessionStoreService,
    private val session: Session
)
    : BaseUseCase<Unit, Unit>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<Unit> {

        // Очищаем все данные сессии (токен, email, password)
        session.clearAllSessionData()

        return CustomResult.success(Unit)
    }

}