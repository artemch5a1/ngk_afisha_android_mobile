package com.example.application.identityService.accountContext.useCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.AccountSession
import java.util.Locale
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<LoginUseCase.Request, AccountSession>(sessionStoreService) {

    data class Request(val email: String, val password: String)

    override val codeMessageMap: Map<Int, String>
        get() = mapOf(
            400 to "Неверный логин или пароль"
        )

    override suspend fun invokeLogic(request: Request): CustomResult<AccountSession> {
        val result = accountRepository
            .takeLoginRequest(request.email.toLowerCase(Locale.ROOT), request.password)

        // Устанавливаем сессию (сохранение будет сделано в SignInViewModel с учётными данными)
        sessionStoreService.setSession(result)

        return CustomResult.success(result)
    }
}