package com.example.domain.identityService.userContext.abstractions.repositories


import com.example.domain.identityService.userContext.models.User
import java.util.UUID

interface UserRepository {

    suspend fun getCurrentUser() : User

    suspend fun updateUser(user: User) : UUID
}