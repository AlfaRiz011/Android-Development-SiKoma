package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.User
import com.example.sikoma.data.repository.UserRepository
import okhttp3.MultipartBody

class UserViewModel(
    private val repository: UserRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getAllUser() = repository.getAllUser()

    fun getUserByID(userId: String) = repository.getUserById(userId)

    fun getUserByEmail(email: String) = repository.getUserByEmail(email)

    fun updateUser(userId: String, user: User) = repository.updateUser(userId, user)

    fun updateUserProfilePic(userId: String, image: MultipartBody.Part?) = repository.updateUserProfilePic(userId, image)
}