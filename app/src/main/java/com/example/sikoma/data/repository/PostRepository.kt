package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Like
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun addPostOrEvent(
        adminId: String,
        adminBody: RequestBody,
        description: RequestBody,
        type: RequestBody,
        eventName: RequestBody,
        eventDate: RequestBody,
        eventTime: RequestBody,
        image: MultipartBody.Part
    ) : LiveData<GenericResponse<Post>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Post>>()
        val client = apiService.createPost(
            adminId = adminId,
            adminBody = adminBody,
            description = description,
            type = type,
            eventName = eventName,
            eventDate = eventDate,
            eventTime = eventTime,
            image = image
        )

        client.enqueue(object : Callback<GenericResponse<Post>> {
            override fun onResponse(
                call: Call<GenericResponse<Post>>,
                response: Response<GenericResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Post>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun deletePostOrEvent(postId: String) : LiveData<GenericResponse<Post>>  {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Post>>()
        val client = apiService.deletePost(postId = postId)

        client.enqueue(object : Callback<GenericResponse<Post>> {
            override fun onResponse(
                call: Call<GenericResponse<Post>>,
                response: Response<GenericResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Post>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun editPostOrEvent(
        adminId: String,
        adminBody: RequestBody,
        description: RequestBody,
        type: RequestBody,
        eventName: RequestBody,
        eventDate: RequestBody,
        eventTime: RequestBody,
        image: MultipartBody.Part
    ) : LiveData<GenericResponse<Post>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Post>>()
        val client = apiService.updatePost(
            adminId = adminId,
            adminBody = adminBody,
            description = description,
            type = type,
            eventName = eventName,
            eventDate = eventDate,
            eventTime = eventTime,
            image = image
        )

        client.enqueue(object : Callback<GenericResponse<Post>> {
            override fun onResponse(
                call: Call<GenericResponse<Post>>,
                response: Response<GenericResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Post>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }


    fun getAllPostsOrEvents() : LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getAllPost()

        client.enqueue(object : Callback<GenericResponse<List<Post>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Post>>>,
                response: Response<GenericResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Post>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getPostByID (postId:String) : LiveData<GenericResponse<Post>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Post>>()
        val client = apiService.getPostById(postId = postId)

        client.enqueue(object : Callback<GenericResponse<Post>> {
            override fun onResponse(
                call: Call<GenericResponse<Post>>,
                response: Response<GenericResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Post>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getPostByAdmin (adminId:String) : LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getPostByAdmin(adminId = adminId)

        client.enqueue(object : Callback<GenericResponse<List<Post>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Post>>>,
                response: Response<GenericResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Post>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getEventPost(): LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getEventPost()

        client.enqueue(object : Callback<GenericResponse<List<Post>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Post>>>,
                response: Response<GenericResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Post>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getEventPostAdmin(adminId: String): LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getEventPostByAdmin(adminId = adminId)

        client.enqueue(object : Callback<GenericResponse<List<Post>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Post>>>,
                response: Response<GenericResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Post>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun likePost(userId: String, postId: String) : LiveData<GenericResponse<Like>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Like>>()
        val client = apiService.likePost(userId = userId, postId = postId)

        client.enqueue(object : Callback<GenericResponse<Like>> {
            override fun onResponse(
                call: Call<GenericResponse<Like>>,
                response: Response<GenericResponse<Like>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Like>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun unlikePost(userId: String, postId: String) : LiveData<GenericResponse<Like>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Like>>()
        val client = apiService.unlikePost(userId = userId, postId = postId)

        client.enqueue(object : Callback<GenericResponse<Like>> {
            override fun onResponse(
                call: Call<GenericResponse<Like>>,
                response: Response<GenericResponse<Like>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Like>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun toggleLike(userId: String, postId: String) : LiveData<GenericResponse<Like>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Like>>()
        val client = apiService.toggleLike(userId = userId, postId = postId)

        client.enqueue(object : Callback<GenericResponse<Like>> {
            override fun onResponse(
                call: Call<GenericResponse<Like>>,
                response: Response<GenericResponse<Like>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Like>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getLikePost(postId: String) : LiveData<GenericResponse<List<Like>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Like>>>()
        val client = apiService.getLikePost(postId = postId)

        client.enqueue(object : Callback<GenericResponse<List<Like>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Like>>>,
                response: Response<GenericResponse<List<Like>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Like>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getRecommendationPost(userId: String) : LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getPostRecommendation(userId = userId)

        client.enqueue(object : Callback<GenericResponse<List<Post>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Post>>>,
                response: Response<GenericResponse<List<Post>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Post>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
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
