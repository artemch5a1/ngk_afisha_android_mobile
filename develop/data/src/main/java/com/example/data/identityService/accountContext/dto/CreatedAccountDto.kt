package com.example.data.identityService.accountContext.dto

import java.util.UUID

data class CreatedAccountDto(
    val accountId: UUID,
    val email: String,
    val role:Int
)
