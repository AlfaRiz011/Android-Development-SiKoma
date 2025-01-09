package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class AdminRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: AdminRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AdminRepository =
            instance ?: synchronized(this) {
                instance ?: AdminRepository(apiService, pref)
            }.also { instance = it }
    }
}
