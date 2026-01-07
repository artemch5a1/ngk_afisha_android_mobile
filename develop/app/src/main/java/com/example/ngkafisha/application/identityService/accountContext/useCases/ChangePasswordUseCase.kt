package com.example.ngkafisha.application.identityService.accountContext.useCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.contracts.ChangePassword
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<ChangePasswordUseCase.Request, UUID>(sessionStoreService) {


    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {

        if(request.oldPassword.isBlank() || request.newPassword.isBlank() || request.repeatPassword.isBlank())
            return CustomResult.failure("Не все поля заполнены")

        if(request.newPassword != request.repeatPassword)
            return CustomResult.failure("Пароли не совпадают")

        val response = accountRepository.changeAccountPassword(
            ChangePassword(
                request.oldPassword,
                request.newPassword
            )
        )

        return CustomResult.success(response)
    }


    data class Request(val oldPassword: String, val newPassword: String, val repeatPassword: String)
}