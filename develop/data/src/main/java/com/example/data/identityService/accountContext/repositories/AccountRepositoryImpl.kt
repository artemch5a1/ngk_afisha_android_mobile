package com.example.data.identityService.accountContext.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.identityService.accountContext.dto.LoginRequest
import com.example.data.identityService.accountContext.mapper.AccountMapper
import com.example.data.identityService.accountContext.mapper.ContractMapper
import com.example.data.identityService.common.remote.IdentityApi
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.contracts.ChangePassword
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession
import com.example.domain.identityService.userContext.models.Student
import java.util.UUID
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : AccountRepository {

    override suspend fun takeLoginRequest(
        email: String,
        password: String
    ): AccountSession {

        val request = LoginRequest(email, password)

        return safeApiCall {
            val response = identityApi.loginAsync(request)
            AccountMapper.toAccountSession(response)
        }
    }

    override suspend fun registryStudent(
        email: String,
        password: String,
        user: Student
    ) {
        val request = AccountMapper.toStudentRegistryDto(email, password, user)

        return safeApiCall {
            identityApi.registryStudent(request)
        }
    }

    override suspend fun getCurrentAccount(): Account {
        return safeApiCall {
            val response = identityApi.getCurrentAccountAsync()

            AccountMapper.toDomain(response)
        }
    }

    override suspend fun getCurrentAccountByToken(accessToken: String): Account {
        return safeApiCall {
            val response = identityApi.getCurrentAccountByTokenAsync("Bearer $accessToken")

            AccountMapper.toDomain(response)
        }
    }

    override suspend fun changeAccountPassword(changePassword: ChangePassword): UUID {
        return safeApiCall {

            identityApi.changePasswordAsync(ContractMapper.toChangePasswordDto(changePassword))

        }
    }

}