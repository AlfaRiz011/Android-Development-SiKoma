package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.repository.AdminRepository
import okhttp3.MultipartBody

class AdminViewModel(
    private val repository: AdminRepository,
    private val pref: UserPreferences
) : ViewModel() {

    var preferences = pref
    var isLoading = repository.isLoading

    fun getAllAdmins() = repository.getAllAdmins()

    fun getAdminByID(adminId: String) = repository.getAdminById(adminId)

    fun getAdminByName(adminName: String) = repository.getAdminByName(adminName)

    fun updateAdmin(adminId: String, admin: Admin) = repository.updateAdmin(adminId, admin)

    fun updateAdminProfilePic(adminId: String, image: MultipartBody.Part?) = repository.updateAdminProfilePic(adminId, image)
}
