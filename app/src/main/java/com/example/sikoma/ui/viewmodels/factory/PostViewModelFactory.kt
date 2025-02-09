package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.PostRepository
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.PostViewModel

class PostViewModelFactory (
    private val repository: PostRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: PostViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): PostViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: PostViewModelFactory(
                    Injection.providePostRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}
