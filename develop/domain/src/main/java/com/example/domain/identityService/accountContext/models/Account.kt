package com.example.domain.identityService.accountContext.models

import com.example.domain.common.enums.Role
import java.util.UUID

data class Account(
    val accountId: UUID,
    val email: String,
    val accountRole: Role
)
