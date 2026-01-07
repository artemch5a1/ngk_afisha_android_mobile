package com.example.ngkafisha.application.identityService.userContext.useCases.userUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.UserRepository
import com.example.domain.identityService.userContext.models.User
import com.example.ngkafisha.application.common.base.BaseUseCase
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val userRepository: UserRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<Unit, User>(sessionStoreService) {

    override suspend fun invokeLogic(request: Unit): CustomResult<User> {
        val result = userRepository.getCurrentUser()

        return CustomResult.success(result)
    }

}