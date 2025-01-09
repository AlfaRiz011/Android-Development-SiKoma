package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.remote.response.GenericResponse

class AuthRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun register(registerBodyRequest: RegisterBodyRequest) {

    }

    fun login(loginBodyRequest: LoginBodyRequest)  {

    }

    fun requestOtp(otpBodyRequest: OtpBodyRequest) {

    }

    fun verifyOtp(otpBodyRequest: OtpBodyRequest){

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
