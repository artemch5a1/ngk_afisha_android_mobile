package com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories

import com.example.ngkafisha.domain.identityService.userContext.models.User
import java.util.UUID

interface UserRepository {

    suspend fun getCurrentUser() : User

    suspend fun updateUser(user: User) : UUID
}