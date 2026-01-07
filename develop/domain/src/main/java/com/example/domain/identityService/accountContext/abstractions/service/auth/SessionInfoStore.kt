package com.example.domain.identityService.accountContext.abstractions.service.auth

import com.example.domain.common.models.ReadOnlyEvent
import com.example.domain.identityService.accountContext.models.Account


interface SessionInfoStore {
    val isAuth: Boolean
    val currentAccount: Account?
    val accessToken: String?

    data class SessionChangedData(val sessionInfoStore: SessionInfoStore, val message: String? = null)

    val sessionChanged : ReadOnlyEvent<SessionChangedData>
}