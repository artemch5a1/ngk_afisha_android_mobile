package com.example.application.identityService.accountContext.useCases

import com.example.application.common.base.BaseUseCase
import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<ValidateTokenUseCase.Request, Account>(sessionStoreService) {

    data class Request(val accessToken: String)

    override suspend fun invokeLogic(request: Request): CustomResult<Account> {
        val result = accountRepository.getCurrentAccountByToken(request.accessToken)
        return CustomResult.success(result)
    }
}
