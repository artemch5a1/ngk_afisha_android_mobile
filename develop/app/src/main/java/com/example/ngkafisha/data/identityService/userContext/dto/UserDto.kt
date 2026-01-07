package com.example.ngkafisha.data.identityService.userContext.dto

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserDto(
    val userId: UUID,
    val surname: String,
    val name: String,
    val patronymic: String,
    val birthDate: String
)
