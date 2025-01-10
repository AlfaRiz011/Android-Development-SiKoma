package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository,
    private val pref: UserPreferences
): ViewModel() {

    var preferences = pref
    var isLoading = repository.isLoading

    fun register(registerData: RegisterBodyRequest) = repository.register(registerData)

    fun login(loginData: LoginBodyRequest) = repository.login(loginData)

    fun requestOTP(registerData: Register) = repository.requestOtp(registerData)

    fun verifyOTP(otpData: OtpBodyRequest) = repository.verifyOtp(otpData)
}