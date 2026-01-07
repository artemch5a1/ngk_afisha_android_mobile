package com.example.data.identityService.accountContext.dto
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AccountDto(
    val accountId: UUID,
    val email: String,
    val accountRole: Int
)
