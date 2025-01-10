package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.FollowAdmin
import com.example.sikoma.data.models.FollowTag
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowRepository (
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun followTag(userId: String, tagId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<FollowTag>>()
        val client = apiService.followTag(userId = userId, tagId = tagId)

        client.enqueue(object : Callback<GenericResponse<FollowTag>> {
            override fun onResponse(
                call: Call<GenericResponse<FollowTag>>,
                response: Response<GenericResponse<FollowTag>>
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

            override fun onFailure(call: Call<GenericResponse<FollowTag>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun followAdmin(userId: String, adminId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<FollowAdmin>>()
        val client = apiService.followAdmin(userId = userId, adminId = adminId)

        client.enqueue(object : Callback<GenericResponse<FollowAdmin>> {
            override fun onResponse(
                call: Call<GenericResponse<FollowAdmin>>,
                response: Response<GenericResponse<FollowAdmin>>
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

            override fun onFailure(call: Call<GenericResponse<FollowAdmin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun unfollowTag(userId: String, tagId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<FollowTag>>()
        val client = apiService.unfollowTag(userId = userId, tagId = tagId)

        client.enqueue(object : Callback<GenericResponse<FollowTag>> {
            override fun onResponse(
                call: Call<GenericResponse<FollowTag>>,
                response: Response<GenericResponse<FollowTag>>
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

            override fun onFailure(call: Call<GenericResponse<FollowTag>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun unfollowAdmin(userId: String, adminId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<FollowAdmin>>()
        val client = apiService.unfollowAdmin(userId = userId, adminId = adminId)

        client.enqueue(object : Callback<GenericResponse<FollowAdmin>> {
            override fun onResponse(
                call: Call<GenericResponse<FollowAdmin>>,
                response: Response<GenericResponse<FollowAdmin>>
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

            override fun onFailure(call: Call<GenericResponse<FollowAdmin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    companion object {
        @Volatile
        private var instance: FollowRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): FollowRepository =
            instance ?: synchronized(this) {
                instance ?: FollowRepository(apiService, pref)
            }.also { instance = it }
    }
}