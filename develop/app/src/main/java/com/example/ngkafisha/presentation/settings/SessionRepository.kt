package com.example.ngkafisha.presentation.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionPersistence
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val SESSION_DATASTORE_NAME = "session"

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(
    name = SESSION_DATASTORE_NAME
)

@Singleton
class SessionRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionPersistence {
    private companion object {
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_PASSWORD = stringPreferencesKey("password")
    }

    override suspend fun getAccessToken(): String? {
        return context.sessionDataStore.data.map { it[KEY_ACCESS_TOKEN] }.first()
    }

    override suspend fun getSavedEmail(): String? {
        return context.sessionDataStore.data.map { it[KEY_EMAIL] }.first()
    }

    override suspend fun getSavedPassword(): String? {
        return context.sessionDataStore.data.map { it[KEY_PASSWORD] }.first()
    }

    override suspend fun saveSession(accessToken: String, email: String, password: String) {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessToken
            prefs[KEY_EMAIL] = email
            prefs[KEY_PASSWORD] = password
        }
    }

    override suspend fun saveCredentials(email: String, password: String) {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
            prefs[KEY_PASSWORD] = password
        }
    }

    override suspend fun clearSession() {
        context.sessionDataStore.edit { prefs ->
            prefs.remove(KEY_ACCESS_TOKEN)
            prefs.remove(KEY_EMAIL)
            prefs.remove(KEY_PASSWORD)
        }
    }

    override suspend fun clearToken() {
        context.sessionDataStore.edit { prefs ->
            prefs.remove(KEY_ACCESS_TOKEN)
        }
    }
}
