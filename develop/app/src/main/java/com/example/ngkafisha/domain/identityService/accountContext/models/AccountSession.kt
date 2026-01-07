package com.example.ngkafisha.domain.identityService.accountContext.models

data class AccountSession (
    val account: Account,
    val accessToken: String
)