package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class UserRepository(
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}