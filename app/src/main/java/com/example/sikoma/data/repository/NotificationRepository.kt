package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository (
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun createNotification(userId: String, postId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Notification>>()
        val client = apiService.createNotification(userId = userId, postId = postId)

        client.enqueue(object : Callback<GenericResponse<Notification>> {
            override fun onResponse(
                call: Call<GenericResponse<Notification>>,
                response: Response<GenericResponse<Notification>>
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

            override fun onFailure(call: Call<GenericResponse<Notification>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun updateAllNotifications(userId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Notification>>()
        val client = apiService.updateAllNotifications(userId = userId)

        client.enqueue(object : Callback<GenericResponse<Notification>> {
            override fun onResponse(
                call: Call<GenericResponse<Notification>>,
                response: Response<GenericResponse<Notification>>
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

            override fun onFailure(call: Call<GenericResponse<Notification>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun updateOneNotifications(notifId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Notification>>()
        val client = apiService.updateOneNotifications(notifId = notifId)

        client.enqueue(object : Callback<GenericResponse<Notification>> {
            override fun onResponse(
                call: Call<GenericResponse<Notification>>,
                response: Response<GenericResponse<Notification>>
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

            override fun onFailure(call: Call<GenericResponse<Notification>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun getUserNotificationPost(userId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Notification>>>()
        val client = apiService.getUserNotificationPost(userId = userId)

        client.enqueue(object : Callback<GenericResponse<List<Notification>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Notification>>>,
                response: Response<GenericResponse<List<Notification>>>
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

            override fun onFailure(call: Call<GenericResponse<List<Notification>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun getUserNotificationEvent(userId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Notification>>>()
        val client = apiService.getUserNotificationEvent(userId = userId)

        client.enqueue(object : Callback<GenericResponse<List<Notification>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Notification>>>,
                response: Response<GenericResponse<List<Notification>>>
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

            override fun onFailure(call: Call<GenericResponse<List<Notification>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun getNotifById(notifId: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Notification>>()
        val client = apiService.getNotifById(notifId = notifId)

        client.enqueue(object : Callback<GenericResponse<Notification>> {
            override fun onResponse(
                call: Call<GenericResponse<Notification>>,
                response: Response<GenericResponse<Notification>>
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

            override fun onFailure(call: Call<GenericResponse<Notification>>, t: Throwable) {
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
        private var instance: NotificationRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): NotificationRepository =
            instance ?: synchronized(this) {
                instance ?: NotificationRepository(apiService, pref)
            }.also { instance = it }
    }
}