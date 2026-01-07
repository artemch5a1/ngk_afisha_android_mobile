package com.example.ngkafisha.application.identityService.accountContext.useCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class GetCurrentAccount@Inject constructor(
    private val accountRepository: AccountRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, Account>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<Account> {
        val result = accountRepository.getCurrentAccount()

        return CustomResult.success(result)
    }
}