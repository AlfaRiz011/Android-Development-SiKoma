package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class NotificationRepository (
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun createNotification(userId: String, postId: String) {

    }

    fun updateAllNotifications(userId: String) {

    }

    fun updateOneNotifications(notifId: String) {

    }

    fun getUserNotificationPost(userId: String) {

    }

    fun getUserNotificationEvent(userId: String) {

    }

    fun getNotifById(notifId: String) {

    }

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