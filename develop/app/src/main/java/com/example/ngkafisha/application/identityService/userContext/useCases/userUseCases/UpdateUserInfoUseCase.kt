package com.example.ngkafisha.application.identityService.userContext.useCases.userUseCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.abstractions.repositories.UserRepository
import com.example.domain.identityService.userContext.models.User
import com.example.ngkafisha.application.common.base.BaseUseCase
import java.util.UUID
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<UpdateUserInfoUseCase.Request, UUID>(sessionStoreService) {


    override suspend fun invokeLogic(request: Request): CustomResult<UUID> {
        val response = userRepository.updateUser(request.user)

        return CustomResult.Companion.success(response)
    }


    data class Request(val user: User)

}