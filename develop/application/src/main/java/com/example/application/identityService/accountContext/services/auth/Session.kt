package com.example.application.identityService.accountContext.services.auth

import com.example.domain.common.models.CustomEvent
import com.example.domain.common.models.ReadOnlyEvent
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionPersistence
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession

class Session(
    private val sessionPersistence: SessionPersistence? = null
) : SessionInfoStore, SessionStoreService {

    private val _sessionChanged = CustomEvent<SessionInfoStore.SessionChangedData>()
    override val sessionChanged : ReadOnlyEvent<SessionInfoStore.SessionChangedData> =
        ReadOnlyEvent(_sessionChanged)

    private var _currentSession: AccountSession? = null

    override val isAuth: Boolean
        get() = _currentSession != null

    override val currentAccount: Account?
        get() = _currentSession?.account

    override val accessToken: String?
        get() = _currentSession?.accessToken


    override fun setSession(accountSession: AccountSession) {
        _currentSession = accountSession
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    suspend fun setSessionWithSave(accountSession: AccountSession) {
        _currentSession = accountSession
        sessionPersistence?.saveSession(
            accountSession.accessToken,
            accountSession.account.email,
            ""
        )
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    suspend fun setSessionWithCredentials(accountSession: AccountSession, email: String, password: String) {
        _currentSession = accountSession
        sessionPersistence?.saveSession(accountSession.accessToken, email, password)
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    suspend fun loadSessionFromStorage(): Boolean {
        val token = sessionPersistence?.getAccessToken()
        return token != null
    }

    override fun resetSession() {
        _currentSession = null
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    suspend fun resetSessionWithClear() {
        _currentSession = null
        sessionPersistence?.clearToken()
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    override fun resetSession(message: String) {
        _currentSession = null
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this, message))
    }

    suspend fun resetSessionWithClear(message: String) {
        _currentSession = null
        sessionPersistence?.clearToken()
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this, message))
    }

    suspend fun clearAllSessionData() {
        _currentSession = null
        sessionPersistence?.clearSession()
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }
}