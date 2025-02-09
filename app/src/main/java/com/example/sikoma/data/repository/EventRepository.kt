package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.EventParticipant
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository (
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun getParticipant(postId: String): LiveData<GenericResponse<List<EventParticipant>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<EventParticipant>>>()
        val client = apiService.getParticipant(postId = postId)

        client.enqueue(object : Callback<GenericResponse<List<EventParticipant>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<EventParticipant>>>,
                response: Response<GenericResponse<List<EventParticipant>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse<List<EventParticipant>>(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<EventParticipant>>>, t: Throwable) {
                resultLiveData.value = GenericResponse<List<EventParticipant>>(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun getParticipantUserId(userId: String): LiveData<GenericResponse<List<Post>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Post>>>()
        val client = apiService.getParticipantUserId(userId = userId)

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


    fun participateEvent(postId: String, userId: String): LiveData<GenericResponse<EventParticipant>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<EventParticipant>>()
        val client = apiService.participateEvent(postId = postId, userId = userId)

        client.enqueue(object : Callback<GenericResponse<EventParticipant>> {
            override fun onResponse(
                call: Call<GenericResponse<EventParticipant>>,
                response: Response<GenericResponse<EventParticipant>>
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

            override fun onFailure(call: Call<GenericResponse<EventParticipant>>, t: Throwable) {
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

    fun deleteParticipant(postId: String, userId: String): LiveData<GenericResponse<EventParticipant>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<EventParticipant>>()
        val client = apiService.deleteParticipant(postId = postId, userId = userId)

        client.enqueue(object : Callback<GenericResponse<EventParticipant>> {
            override fun onResponse(
                call: Call<GenericResponse<EventParticipant>>,
                response: Response<GenericResponse<EventParticipant>>
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

            override fun onFailure(call: Call<GenericResponse<EventParticipant>>, t: Throwable) {
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