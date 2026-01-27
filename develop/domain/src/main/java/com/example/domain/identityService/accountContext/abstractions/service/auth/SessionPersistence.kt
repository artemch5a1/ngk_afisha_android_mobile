package com.example.domain.identityService.accountContext.abstractions.service.auth

interface SessionPersistence {
    suspend fun getAccessToken(): String?
    suspend fun getSavedEmail(): String?
    suspend fun getSavedPassword(): String?
    suspend fun saveSession(accessToken: String, email: String, password: String)
    suspend fun saveCredentials(email: String, password: String)
    suspend fun clearSession()
    suspend fun clearToken()
}
