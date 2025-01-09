package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class NotificationRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: NotificationRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): NotificationRepository =
            instance ?: synchronized(this) {
                instance ?: NotificationRepository(apiService, pref)
            }.also { instance = it }
    }
}