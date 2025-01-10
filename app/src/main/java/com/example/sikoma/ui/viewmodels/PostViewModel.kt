package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostViewModel(
    private val repository: PostRepository,
    pref: UserPreferences
) : ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading
    val postResult = MutableLiveData<GenericResponse<Post>>()

    fun getAllPost() = repository.getAllPostsOrEvents()

    fun getEventPost() = repository.getEventPost()

    fun getPostById(postId: String) = repository.getPostByID(postId)

    fun getRecommendationPost(userId: String) = repository.getRecommendationPost(userId)

    fun getPostAdmin(adminId: String) = repository.getPostByAdmin(adminId)

    fun getEventPostAdmin(adminId: String) = repository.getEventPostAdmin(adminId)

    fun getLikePost(postId: String) = repository.getLikePost(postId)

    fun likePost(userId: String, postId: String) = repository.likePost(userId, postId)

    fun unlikePost(userId: String, postId: String) = repository.unlikePost(userId, postId)

    fun toggleLike(userId: String, postId: String) = repository.toggleLike(userId, postId)

    fun deletePost(postId: String) = repository.deletePostOrEvent(postId)

    fun addPost(
        adminId: String,
        adminBody: RequestBody,
        description: RequestBody,
        type: RequestBody,
        eventName: RequestBody,
        eventDate: RequestBody,
        eventTime: RequestBody,
        image: MultipartBody.Part
    ) {
        repository.addPostOrEvent(
            adminId,
            adminBody,
            description,
            type,
            eventName,
            eventDate,
            eventTime,
            image
        )
    }

    fun editPost(
        adminId: String,
        adminBody: RequestBody,
        description: RequestBody,
        type: RequestBody,
        eventName: RequestBody,
        eventDate: RequestBody,
        eventTime: RequestBody,
        image: MultipartBody.Part
    ) {
        repository.editPostOrEvent(
            adminId,
            adminBody,
            description,
            type,
            eventName,
            eventDate,
            eventTime,
            image
        )
    }
}
