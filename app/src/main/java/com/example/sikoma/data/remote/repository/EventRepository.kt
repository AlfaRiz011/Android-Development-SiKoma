package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class EventRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, pref)
            }.also { instance = it }
    }
}