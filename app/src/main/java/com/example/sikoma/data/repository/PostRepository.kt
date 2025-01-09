package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.config.ApiService

class PostRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun addPostOrEvent() {

    }

    fun deletePostOrEvent(postId: String) {

    }

    fun editPostOrEvent(postId: String, post: Post) {

    }

    fun getAllPostsOrEvents() {

    }

    fun likePost(userId: String, postId: String) {

    }

    fun unlikePost(userId: String, postId: String) {

    }

    companion object {
        @Volatile
        private var instance: PostRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): PostRepository =
            instance ?: synchronized(this) {
                instance ?: PostRepository(apiService, pref)
            }.also { instance = it }
    }
}
