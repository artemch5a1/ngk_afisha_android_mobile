package com.example.ngkafisha.presentation.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val SETTINGS_DATASTORE_NAME = "settings"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS_DATASTORE_NAME
)

@Singleton
class ThemeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private companion object {
        val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
        // legacy key (migrate read-only)
        val KEY_DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val themeMode: Flow<ThemeMode> =
        context.dataStore.data
            .map { prefs ->
                val savedMode = prefs[KEY_THEME_MODE]
                if (savedMode != null) {
                    ThemeMode.entries.firstOrNull { it.name == savedMode } ?: ThemeMode.SYSTEM
                } else {
                    // Backward compatibility: boolean-only setting.
                    when (prefs[KEY_DARK_THEME]) {
                        true -> ThemeMode.DARK
                        false -> ThemeMode.LIGHT
                        null -> ThemeMode.SYSTEM
                    }
                }
            }
            .distinctUntilChanged()

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_MODE] = mode.name
        }
    }
}

