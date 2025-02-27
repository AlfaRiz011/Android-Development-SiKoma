package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.AdminRepository
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.AdminViewModel

class AdminViewModelFactory (
    private val repository: AdminRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: AdminViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): AdminViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: AdminViewModelFactory(
                    Injection.provideAdminRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}