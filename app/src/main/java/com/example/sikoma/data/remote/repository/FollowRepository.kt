package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class FollowRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun followTag(userId: String, tagId: String) {

    }

    fun followAdmin(userId: String, adminId: String) {

    }

    fun unfollowTag(userId: String, tagId: String) {

    }

    fun unfollowAdmin(userId: String, adminId: String) {

    }

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