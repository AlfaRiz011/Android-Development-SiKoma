package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.FollowAdmin
import com.example.sikoma.data.models.FollowTag
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.FollowRepository

class FollowViewModel(
    private val repository: FollowRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getFollowAdmin(userId: String): LiveData<GenericResponse<List<FollowAdmin>>> {
        return repository.getFollowAdmin(userId)
    }

    fun getFollowTag(userId: String): LiveData<GenericResponse<List<FollowTag>>> {
        return repository.getFollowTag(userId)
    }

    fun followAdmin(userId:String, adminId: String): LiveData<GenericResponse<FollowAdmin>> {
        return repository.followAdmin(userId, adminId)
    }

    fun followTag(userId:String, tagId: String): LiveData<GenericResponse<FollowTag>> {
        return repository.followTag(userId, tagId)
    }

    fun unfollowAdmin(userId:String, adminId: String): LiveData<GenericResponse<FollowAdmin>> {
        return repository.unfollowAdmin(userId, adminId)
    }

    fun unfollowTag(userId:String, tagId: String): LiveData<GenericResponse<FollowTag>> {
        return repository.unfollowTag(userId, tagId)
    }

}
