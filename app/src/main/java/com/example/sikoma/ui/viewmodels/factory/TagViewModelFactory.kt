package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.TagRepository
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.TagViewModel

class TagViewModelFactory(
    private val repository: TagRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            return TagViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: TagViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): TagViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: TagViewModelFactory(
                    Injection.provideTagRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}