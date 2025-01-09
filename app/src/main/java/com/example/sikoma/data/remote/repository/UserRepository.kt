package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.config.ApiService
import okhttp3.MultipartBody

class UserRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllUser() {

    }

    fun getUserById(id: String) {

    }

    fun getUserByEmail(email: String) {

    }

    fun updateUser(id: String, user: User) {

    }

    fun updateUserProfilePic(id: String, image: MultipartBody.Part?) {
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}
