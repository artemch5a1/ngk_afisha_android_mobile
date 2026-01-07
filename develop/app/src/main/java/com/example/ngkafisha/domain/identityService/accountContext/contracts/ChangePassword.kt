package com.example.ngkafisha.domain.identityService.accountContext.contracts

data class ChangePassword(
    val oldPassword: String,
    val newPassword: String
)
