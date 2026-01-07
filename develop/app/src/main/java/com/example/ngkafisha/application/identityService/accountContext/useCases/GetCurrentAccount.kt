package com.example.ngkafisha.application.identityService.accountContext.useCases

import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.domain.common.models.CustomResult
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.accountContext.models.Account
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