package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Like
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

    fun getAllPost() : LiveData<GenericResponse<List<Post>>> {
        return repository.getAllPostsOrEvents()
    }

    fun getEventPost() : LiveData<GenericResponse<List<Post>>>  {
        return repository.getEventPost()
    }

    fun getPostById(postId: String) : LiveData<GenericResponse<Post>>  {
        return repository.getPostByID(postId)
    }

    fun getRecommendationPost(userId: String) : LiveData<GenericResponse<List<Post>>>  {
        return repository.getRecommendationPost(userId)
    }

    fun getPostAdmin(adminId: String) : LiveData<GenericResponse<List<Post>>>  {
        return repository.getPostByAdmin(adminId)
    }

    fun getEventPostAdmin(adminId: String) : LiveData<GenericResponse<List<Post>>> {
        return repository.getEventPostAdmin(adminId)
    }

    fun getLikePost(postId: String): LiveData<GenericResponse<List<Like>>>   {
        return repository.getLikePost(postId)
    }

    fun likePost(userId: String, postId: String) : LiveData<GenericResponse<Like>> {
        return repository.likePost(userId, postId)
    }

    fun unlikePost(userId: String, postId: String): LiveData<GenericResponse<Like>> {
        return repository.unlikePost(userId, postId)
    }

    fun toggleLike(userId: String, postId: String) : LiveData<GenericResponse<Like>>  {
        return repository.toggleLike(userId, postId)
    }

    fun deletePost(postId: String) : LiveData<GenericResponse<Post>> {
        return repository.deletePostOrEvent(postId)
    }

    fun addPost(
        adminId: String,
        adminBody: RequestBody,
        description: RequestBody,
        type: RequestBody,
        eventName: RequestBody,
        eventDate: RequestBody,
        eventTime: RequestBody,
        image: MultipartBody.Part
    ) : LiveData<GenericResponse<Post>> {
        return repository.addPostOrEvent(
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
    ) : LiveData<GenericResponse<Post>> {
        return repository.editPostOrEvent(
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
