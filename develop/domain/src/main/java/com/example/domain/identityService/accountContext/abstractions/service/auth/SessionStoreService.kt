package com.example.domain.identityService.accountContext.abstractions.service.auth

import com.example.domain.identityService.accountContext.models.AccountSession


interface SessionStoreService {

    fun setSession(accountSession: AccountSession)
    fun resetSession()

    fun resetSession(message: String)
}