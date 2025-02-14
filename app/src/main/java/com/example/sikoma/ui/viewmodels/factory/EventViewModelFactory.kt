package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.EventRepository
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.EventViewModel

class EventViewModelFactory (
    private val repository: EventRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(repository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: EventViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): EventViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: EventViewModelFactory(
                    Injection.provideEventRepository(context),
                    Injection.providePreferences(context)
                ).also { instance = it }
            }
        }
    }
}