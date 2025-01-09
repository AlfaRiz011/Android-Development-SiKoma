package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.remote.config.ApiService

class AuthRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun register(){

    }

    fun requestOtp(){

    }

    fun verifyOtp(){

    }

    fun login(){

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