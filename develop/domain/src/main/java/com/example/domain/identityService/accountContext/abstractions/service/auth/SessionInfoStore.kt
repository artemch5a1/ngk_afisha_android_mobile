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

    /**
     * Устанавливает сессию и сохраняет токен в хранилище (без пароля)
     */
    suspend fun setSessionWithSave(accountSession: AccountSession)

    /**
     * Устанавливает сессию и сохраняет токен с учётными данными (email и password)
     */
    suspend fun setSessionWithCredentials(accountSession: AccountSession, email: String, password: String)

    /**
     * Очищает токен из хранилища, сохраняя email и password для автозаполнения
     */
    suspend fun resetSessionWithClear()

    /**
     * Очищает токен из хранилища с сообщением, сохраняя email и password для автозаполнения
     */
    suspend fun resetSessionWithClear(message: String)

    /**
     * Полностью очищает все данные сессии (токен, email, password)
     */
    suspend fun clearAllSessionData()

    /**
     * Проверяет, есть ли сохранённый токен в хранилище
     */
    suspend fun loadSessionFromStorage(): Boolean
}