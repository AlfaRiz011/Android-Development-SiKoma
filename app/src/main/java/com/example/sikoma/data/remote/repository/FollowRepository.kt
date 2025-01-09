package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class FollowRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: FollowRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): FollowRepository =
            instance ?: synchronized(this) {
                instance ?: FollowRepository(apiService, pref)
            }.also { instance = it }
    }
}