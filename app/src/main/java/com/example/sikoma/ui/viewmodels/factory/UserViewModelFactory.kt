package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.UserRepository
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.UserViewModel

class UserViewModelFactory(
    private val repository: UserRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): UserViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: UserViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}
