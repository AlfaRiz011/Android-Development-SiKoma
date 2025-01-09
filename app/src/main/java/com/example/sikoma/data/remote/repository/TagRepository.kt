package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class TagRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: TagRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): TagRepository =
            instance ?: synchronized(this) {
                instance ?: TagRepository(apiService, pref)
            }.also { instance = it }
    }
}