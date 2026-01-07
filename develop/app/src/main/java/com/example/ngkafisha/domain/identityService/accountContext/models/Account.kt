package com.example.ngkafisha.domain.identityService.accountContext.models

import com.example.ngkafisha.domain.common.enums.Role
import java.util.UUID

data class Account(
    val accountId: UUID,
    val email: String,
    val accountRole: Role
)
