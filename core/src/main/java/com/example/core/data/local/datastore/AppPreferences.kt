package com.example.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferences(private val context: Context) {
    private val DARK_THEME = booleanPreferencesKey("dark_theme")
    private val AMOLED_MODE = booleanPreferencesKey("amoled_mode")
    private val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    private val DAILY_LIMIT = intPreferencesKey("daily_limit")
    private val DEFAULT_SIM_SLOT = intPreferencesKey("default_sim_slot")

    suspend fun isDarkTheme(): Boolean {
        return context.dataStore.data.map { it[DARK_THEME] ?: false }.first()
    }

    suspend fun isAmoledMode(): Boolean {
        return context.dataStore.data.map { it[AMOLED_MODE] ?: false }.first()
    }

    suspend fun isDynamicColor(): Boolean {
        return context.dataStore.data.map { it[DYNAMIC_COLOR] ?: true }.first()
    }

    suspend fun getDailyLimit(): Int {
        return context.dataStore.data.map { it[DAILY_LIMIT] ?: 100 }.first()
    }

    suspend fun getDefaultSimSlot(): Int {
        return context.dataStore.data.map { it[DEFAULT_SIM_SLOT] ?: 0 }.first()
    }

    suspend fun setDarkTheme(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME] = value
        }
    }

    suspend fun setAmoledMode(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[AMOLED_MODE] = value
        }
    }

    suspend fun setDynamicColor(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DYNAMIC_COLOR] = value
        }
    }

    suspend fun setDailyLimit(value: Int) {
        context.dataStore.edit { prefs ->
            prefs[DAILY_LIMIT] = value
        }
    }

    suspend fun setDefaultSimSlot(value: Int) {
        context.dataStore.edit { prefs ->
            prefs[DEFAULT_SIM_SLOT] = value
        }
    }

    // Flow getters for observing
    fun observeDarkTheme(): Flow<Boolean> {
        return context.dataStore.data.map { it[DARK_THEME] ?: false }
    }

    fun observeAmoledMode(): Flow<Boolean> {
        return context.dataStore.data.map { it[AMOLED_MODE] ?: false }
    }
}