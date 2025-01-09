package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllUser() {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<User>>>()
        val client = apiService.getAllUser()

        client.enqueue(object : Callback<GenericResponse<List<User>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<User>>>,
                response: Response<GenericResponse<List<User>>>
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

            override fun onFailure(call: Call<GenericResponse<List<User>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun getUserById(id: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.getUserById(id)

        client.enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
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

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun getUserByEmail(email: String) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.getUserByEmail(email)

        client.enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
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

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun updateUser(id: String, user: User) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.updateUser(id = id, updateUserRequest = user)

        client.enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            pref.saveUser(it)
                        }
                    }
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun updateUserProfilePic(id: String, image: MultipartBody.Part?) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.updateUserProfilePic(id = id, image = image)

        client.enqueue(object : Callback<GenericResponse<User>> {
            override fun onResponse(
                call: Call<GenericResponse<User>>,
                response: Response<GenericResponse<User>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            pref.saveUser(it)
                        }
                    }
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<User>>, t: Throwable) {
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
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}
