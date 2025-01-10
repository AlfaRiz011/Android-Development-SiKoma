package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.NotificationRepository
import com.example.sikoma.data.repository.TagRepository

class TagViewModel(
    private val repository: TagRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getAllTag() = repository.getAllTags()

    fun getTagByPost(postId:String) = repository.getTagsByPost(postId)

    fun tagPost(postId:String, tagName: String) = repository.tagPost(postId, tagName)
}
