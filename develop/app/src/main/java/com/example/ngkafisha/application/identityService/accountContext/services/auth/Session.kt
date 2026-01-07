package com.example.ngkafisha.application.identityService.accountContext.services.auth

import com.example.domain.common.models.CustomEvent
import com.example.domain.common.models.ReadOnlyEvent
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.accountContext.models.Account
import com.example.domain.identityService.accountContext.models.AccountSession

class Session : SessionInfoStore, SessionStoreService {

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

    override fun resetSession() {
        _currentSession = null
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this))
    }

    override fun resetSession(message: String) {
        _currentSession = null
        _sessionChanged.invoke(SessionInfoStore.SessionChangedData(this, message))
    }
}