package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Tag
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TagRepository (
    private val apiService: ApiService,
    private val pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllTags() : LiveData<GenericResponse<List<Tag>>>{
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Tag>>>()
        val client = apiService.getAllTags()

        client.enqueue(object : Callback<GenericResponse<List<Tag>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Tag>>>,
                response: Response<GenericResponse<List<Tag>>>
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

            override fun onFailure(call: Call<GenericResponse<List<Tag>>>, t: Throwable) {
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

    fun getTagsByPost(postId: String) : LiveData<GenericResponse<List<Tag>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Tag>>>()
        val client = apiService.getTagsByPost(postId = postId)

        client.enqueue(object : Callback<GenericResponse<List<Tag>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Tag>>>,
                response: Response<GenericResponse<List<Tag>>>
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

            override fun onFailure(call: Call<GenericResponse<List<Tag>>>, t: Throwable) {
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


    fun tagPost(postId: String, tagName: String) : LiveData<GenericResponse<Tag>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Tag>>()
        val client = apiService.tagPost(postId = postId, tagName = tagName)

        client.enqueue(object : Callback<GenericResponse<Tag>> {
            override fun onResponse(
                call: Call<GenericResponse<Tag>>,
                response: Response<GenericResponse<Tag>>
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

            override fun onFailure(call: Call<GenericResponse<Tag>>, t: Throwable) {
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