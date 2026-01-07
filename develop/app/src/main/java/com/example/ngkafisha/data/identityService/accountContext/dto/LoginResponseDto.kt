package com.example.ngkafisha.data.identityService.accountContext.dto

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class LoginResponseDto(
    val accountId: UUID,
    val email: String,
    val role: Int,
    val accessToken: String
)
