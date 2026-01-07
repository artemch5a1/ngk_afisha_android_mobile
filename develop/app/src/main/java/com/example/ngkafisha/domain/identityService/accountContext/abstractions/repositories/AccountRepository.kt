package com.example.ngkafisha.domain.identityService.accountContext.abstractions.repositories

import com.example.ngkafisha.domain.identityService.accountContext.contracts.ChangePassword
import com.example.ngkafisha.domain.identityService.accountContext.models.Account
import com.example.ngkafisha.domain.identityService.accountContext.models.AccountSession
import com.example.ngkafisha.domain.identityService.userContext.models.Student
import java.util.UUID

interface AccountRepository {

    suspend fun takeLoginRequest(email: String, password: String): AccountSession

    suspend fun registryStudent(email: String, password: String, user: Student)

    suspend fun getCurrentAccount() : Account

    suspend fun changeAccountPassword(changePassword: ChangePassword) : UUID
}