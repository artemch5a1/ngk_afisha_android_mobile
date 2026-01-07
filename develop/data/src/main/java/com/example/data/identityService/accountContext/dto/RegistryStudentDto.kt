package com.example.data.identityService.accountContext.dto

data class RegistryStudentDto(
    val email: String,
    val password: String,
    val surname: String,
    val name: String,
    val patronymic: String?,
    val dateBirth: String,
    val groupId: Int
)
