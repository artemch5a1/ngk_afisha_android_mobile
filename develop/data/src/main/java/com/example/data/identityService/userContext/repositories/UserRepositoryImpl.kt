package com.example.data.identityService.userContext.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.identityService.common.remote.IdentityApi
import com.example.data.identityService.userContext.mapper.UserMapper
import com.example.domain.identityService.userContext.abstractions.repositories.UserRepository
import com.example.domain.identityService.userContext.models.User
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : UserRepository {
    override suspend fun getCurrentUser(): User {
        return safeApiCall {
            val response = identityApi.getCurrentUserAsync()

            UserMapper.toDomain(response)
        }
    }

    override suspend fun updateUser(user: User): UUID {
        return safeApiCall {
            identityApi.updateUserInfo(UserMapper.toUpdateUserInfo(user))
        }
    }
}