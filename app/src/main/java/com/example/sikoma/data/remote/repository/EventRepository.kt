package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class EventRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun getParticipant(postId: String) {

    }

    fun participateEvent(postId: String, userId: String) {

    }

    fun deleteParticipant(postId: String, userId: String) {

    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, pref)
            }.also { instance = it }
    }
}