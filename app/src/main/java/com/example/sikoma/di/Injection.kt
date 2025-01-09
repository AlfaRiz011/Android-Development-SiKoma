package com.example.sikoma.di

import android.content.Context
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.local.dataStore
import com.example.sikoma.data.remote.config.ApiConfig
import com.example.sikoma.data.remote.repository.AdminRepository
import com.example.sikoma.data.remote.repository.AuthRepository
import com.example.sikoma.data.remote.repository.EventRepository
import com.example.sikoma.data.remote.repository.FollowRepository
import com.example.sikoma.data.remote.repository.NotificationRepository
import com.example.sikoma.data.remote.repository.PostRepository
import com.example.sikoma.data.remote.repository.TagRepository
import com.example.sikoma.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiInstance("")
        return AuthRepository.getInstance(apiService, pref)
    }

    fun provideAdminRepository(context: Context): AdminRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return AdminRepository.getInstance(apiService, pref)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideEventRepository(context: Context): EventRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return EventRepository.getInstance(apiService, pref)
    }

    fun provideFollowRepository(context: Context): FollowRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return FollowRepository.getInstance(apiService, pref)
    }

    fun provideNotificationRepository(context: Context): NotificationRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return NotificationRepository.getInstance(apiService, pref)
    }

    fun providePostRepository(context: Context): PostRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return PostRepository.getInstance(apiService, pref)
    }

    fun provideTagRepository(context: Context): TagRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiInstance(token)
        return TagRepository.getInstance(apiService, pref)
    }

    fun providePreferences(context: Context): UserPreferences {
        val pref = UserPreferences.getInstance(context.dataStore)
        return pref
    }
}