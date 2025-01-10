package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.FollowRepository

class FollowViewModel(
    private val repository: FollowRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getFollowAdmin(userId: String) = repository.getFollowAdmin(userId)

    fun getFollowTag(userId: String) = repository.getFollowTag(userId)

    fun followAdmin(userId:String, adminId: String) = repository.followAdmin(userId, adminId)

    fun followTag(userId:String, tagId: String) = repository.followTag(userId, tagId)

    fun unfollowAdmin(userId:String, adminId: String) = repository.unfollowAdmin(userId, adminId)

    fun unfollowTag(userId:String, tagId: String) = repository.unfollowTag(userId, tagId)

}
