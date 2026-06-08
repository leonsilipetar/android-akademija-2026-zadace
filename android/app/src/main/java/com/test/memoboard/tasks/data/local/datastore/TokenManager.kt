package com.test.memoboard.tasks.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val BACKGROUND_KEY = stringPreferencesKey("background_id")
    }

    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    val usernameFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[USERNAME_KEY] }

    val backgroundIdFlow: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[BACKGROUND_KEY] ?: "default" }

    suspend fun saveAuthData(token: String, username: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USERNAME_KEY] = username
        }
    }

    suspend fun saveBackgroundId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[BACKGROUND_KEY] = id
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.clear() }
    }
}
