package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class TagRepository (
    apiService: ApiService,
    pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllTags(){

    }

    fun getTagsByPost(postId: String){

    }

    fun tagPost(postId: String, tagName: String) {
    }

    companion object {
        @Volatile
        private var instance: TagRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): TagRepository =
            instance ?: synchronized(this) {
                instance ?: TagRepository(apiService, pref)
            }.also { instance = it }
    }
}