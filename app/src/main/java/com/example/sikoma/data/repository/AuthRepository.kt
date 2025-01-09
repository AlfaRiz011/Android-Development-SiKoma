package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Login
import com.example.sikoma.data.models.Otp
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.remote.response.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun register(registerBodyRequest: RegisterBodyRequest) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<User>>()
        val client = apiService.register(registerBodyRequest)

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


    fun login(loginBodyRequest: LoginBodyRequest)  {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Login>>()
        val client = apiService.login(loginBodyRequest)

        client.enqueue(object : Callback<GenericResponse<Login>> {
            override fun onResponse(
                call: Call<GenericResponse<Login>>,
                response: Response<GenericResponse<Login>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            if (it.role == "user") {
                                pref.saveUser(it.user)
                                pref.saveRole("user")
                            } else {
                                pref.saveAdmin(it.admin)
                                pref.saveRole("admin")
                            }
                            pref.saveTokenUser(it.token)
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

            override fun onFailure(call: Call<GenericResponse<Login>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun requestOtp(otpBodyRequest: OtpBodyRequest) {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Otp>>()
        val client = apiService.requestOtp(otpBodyRequest)

        client.enqueue(object : Callback<GenericResponse<Otp>> {
            override fun onResponse(
                call: Call<GenericResponse<Otp>>,
                response: Response<GenericResponse<Otp>>
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

            override fun onFailure(call: Call<GenericResponse<Otp>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
    }

    fun verifyOtp(otpBodyRequest: OtpBodyRequest){
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Register>>()
        val client = apiService.verifyOtp(otpBodyRequest)

        client.enqueue(object : Callback<GenericResponse<Register>> {
            override fun onResponse(
                call: Call<GenericResponse<Register>>,
                response: Response<GenericResponse<Register>>
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

            override fun onFailure(call: Call<GenericResponse<Register>>, t: Throwable) {
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
        private var instance: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, pref)
            }.also { instance = it }
    }
}
