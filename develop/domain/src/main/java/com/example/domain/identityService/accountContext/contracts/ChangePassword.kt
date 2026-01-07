package com.example.domain.identityService.accountContext.contracts

data class ChangePassword(
    val oldPassword: String,
    val newPassword: String
)
