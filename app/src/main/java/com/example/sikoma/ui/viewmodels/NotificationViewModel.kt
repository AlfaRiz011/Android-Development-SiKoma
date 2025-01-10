package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.NotificationRepository

class NotificationViewModel(
    private val repository: NotificationRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getNotifById(notifId: String) = repository.getNotifById(notifId)

    fun getNotifPost(userId: String) = repository.getUserNotificationPost(userId)

    fun getNotifEvent(userId: String) = repository.getUserNotificationEvent(userId)

    fun createNotif(userId: String, postId: String) = repository.createNotification(userId, postId)

    fun updateAllNotif(userId: String) = repository.updateAllNotifications(userId)

    fun updateOneNotif(notifId: String) = repository.updateOneNotifications(notifId)

}
