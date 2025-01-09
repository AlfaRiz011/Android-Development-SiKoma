package com.example.sikoma.data.remote.repository

import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class PostRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    companion object {
        @Volatile
        private var instance: PostRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): PostRepository =
            instance ?: synchronized(this) {
                instance ?: PostRepository(apiService, pref)
            }.also { instance = it }
    }
}