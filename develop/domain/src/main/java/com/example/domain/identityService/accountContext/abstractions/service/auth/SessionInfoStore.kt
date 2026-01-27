package com.example.domain.identityService.accountContext.abstractions.service.auth

import com.example.domain.common.models.ReadOnlyEvent
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession


interface SessionInfoStore {
    val isAuth: Boolean
    val currentAccount: Account?
    val accessToken: String?

    data class SessionChangedData(val sessionInfoStore: SessionInfoStore, val message: String? = null)

    val sessionChanged : ReadOnlyEvent<SessionChangedData>

    suspend fun setSessionWithSave(accountSession: AccountSession)

    suspend fun setSessionWithCredentials(accountSession: AccountSession, email: String, password: String)

    suspend fun resetSessionWithClear()

    suspend fun resetSessionWithClear(message: String)

    suspend fun clearAllSessionData()

    suspend fun loadSessionFromStorage(): Boolean
}