package com.example.sikoma.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.remote.config.ApiService
import okhttp3.MultipartBody

class AdminRepository (
    apiService: ApiService,
    pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllAdmins(){

    }

    fun getAdminByName(organizationName: String){

    }

    fun getAdminById(id: String){

    }

    fun updateAdmin(id: String, updateAdminRequest: Admin){

    }

    fun updateAdminProfilePic(id: String, image: MultipartBody.Part?){

    }

    companion object {
        @Volatile
        private var instance: AdminRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AdminRepository =
            instance ?: synchronized(this) {
                instance ?: AdminRepository(apiService, pref)
            }.also { instance = it }
    }
}
