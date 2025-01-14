package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.NotificationRepository

class NotificationViewModel(
    private val repository: NotificationRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getNotifById(notifId: String): LiveData<GenericResponse<Notification>> {
        return repository.getNotifById(notifId)
    }

    fun getNotifPost(userId: String): LiveData<GenericResponse<List<Post>>> {
        return repository.getUserNotificationPost(userId)
    }

    fun getNotifEvent(userId: String): LiveData<GenericResponse<List<Post>>> {
        return repository.getUserNotificationEvent(userId)
    }

    fun createNotif(userId: String, postId: String): LiveData<GenericResponse<Notification>> {
        return repository.createNotification(userId, postId)
    }

    fun updateAllNotif(userId: String): LiveData<GenericResponse<Notification>> {
        return repository.updateAllNotifications(userId)
    }

    fun updateOneNotif(notifId: String): LiveData<GenericResponse<Notification>> {
        return repository.updateOneNotifications(notifId)
    }

}
