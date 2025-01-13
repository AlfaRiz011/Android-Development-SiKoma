package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Login
import com.example.sikoma.data.models.Otp
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository,
    private val pref: UserPreferences
) : ViewModel() {

    var preferences = pref
    var isLoading = repository.isLoading

    fun register(registerData: RegisterBodyRequest): LiveData<GenericResponse<User>> {
        return repository.register(registerData)
    }

    fun login(loginData: LoginBodyRequest): LiveData<GenericResponse<Login>> {
        return repository.login(loginData)
    }

    fun requestOTP(registerData: Register): LiveData<GenericResponse<Otp>> {
        return repository.requestOtp(registerData)
    }

    fun verifyOTP(otpData: OtpBodyRequest): LiveData<GenericResponse<Register>> {
        return repository.verifyOtp(otpData)
    }
}