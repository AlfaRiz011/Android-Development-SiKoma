package com.example.sikoma.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.models.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val USER_KEY = stringPreferencesKey("user_key")
    private val ADMIN_KEY = stringPreferencesKey("admin_key")
    private val SESSION = booleanPreferencesKey("session")
    private val ROLE = stringPreferencesKey("role")

    fun getUserToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveTokenUser(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[ROLE] = role
        }
    }

    fun getRole(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ROLE]
        }
    }

    fun getSession(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SESSION] ?: false
        }
    }

    suspend fun setSession() {
        dataStore.edit { preferences ->
            preferences[SESSION] = true
        }
    }

    suspend fun logOut() {
        dataStore.edit { preferences ->
            preferences.clear()
            preferences[SESSION] = false
        }
    }
    fun getUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val json = preferences[USER_KEY]
            Gson().fromJson(json, User::class.java)
        }
    }

    suspend fun saveUser(user: User?) {
        val json = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[USER_KEY] = json
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }

    fun getAdmin(): Flow<Admin?> {
        return dataStore.data.map { preferences ->
            val json = preferences[ADMIN_KEY]
            Gson().fromJson(json, Admin::class.java)
        }
    }

    suspend fun saveAdmin(admin: Admin?) {
        val json = Gson().toJson(admin)
        dataStore.edit { preferences ->
            preferences[ADMIN_KEY] = json
        }
    }

    suspend fun deleteAdmin() {
        dataStore.edit { preferences ->
            preferences.remove(ADMIN_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}