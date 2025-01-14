package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.AdminRepository
import okhttp3.MultipartBody

class AdminViewModel(
    private val repository: AdminRepository,
    private val pref: UserPreferences
) : ViewModel() {

    var preferences = pref
    var isLoading = repository.isLoading

    fun getAllAdmins(): LiveData<GenericResponse<List<Admin>>> {
        return repository.getAllAdmins()
    }

    fun getAdminByID(adminId: String): LiveData<GenericResponse<Admin>> {
        return repository.getAdminById(adminId)
    }

    fun getAdminByName(adminName: String): LiveData<GenericResponse<Admin>> {
        return repository.getAdminByName(adminName)
    }

    fun updateAdmin(adminId: String, admin: Admin): LiveData<GenericResponse<Admin>> {
        return repository.updateAdmin(adminId, admin)
    }

    fun updateAdminProfilePic(adminId: String, image: MultipartBody.Part?): LiveData<GenericResponse<Admin>> {
        return repository.updateAdminProfilePic(adminId, image)
    }
}
