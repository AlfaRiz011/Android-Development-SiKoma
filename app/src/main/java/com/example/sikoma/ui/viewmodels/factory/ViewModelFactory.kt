package com.example.sikoma.ui.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.*
import com.example.sikoma.di.Injection
import com.example.sikoma.ui.viewmodels.*

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository,
    private val eventRepository: EventRepository,
    private val followRepository: FollowRepository,
    private val notificationRepository: NotificationRepository,
    private val postRepository: PostRepository,
    private val tagRepository: TagRepository,
    private val pref: UserPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            AuthViewModel::class.java -> return AuthViewModel(authRepository, pref) as T
            UserViewModel::class.java -> return UserViewModel(userRepository, pref) as T
            AdminViewModel::class.java -> return AdminViewModel(adminRepository, pref) as T
            EventViewModel::class.java -> return EventViewModel(eventRepository, pref) as T
            FollowViewModel::class.java -> return FollowViewModel(followRepository, pref) as T
            NotificationViewModel::class.java -> return NotificationViewModel(notificationRepository, pref) as T
            PostViewModel::class.java -> return PostViewModel(postRepository, pref) as T
            TagViewModel::class.java -> return TagViewModel(tagRepository, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            instance ?: synchronized(ViewModelFactory::class.java) {
                instance = ViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideUserRepository(context),
                    Injection.provideAdminRepository(context),
                    Injection.provideEventRepository(context),
                    Injection.provideFollowRepository(context),
                    Injection.provideNotificationRepository(context),
                    Injection.providePostRepository(context),
                    Injection.provideTagRepository(context),
                    Injection.providePreferences(context)
                )
            }
            return instance as ViewModelFactory
        }
    }
}
