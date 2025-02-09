package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Tag
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.TagRepository

class TagViewModel(
    private val repository: TagRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getAllTag(): LiveData<GenericResponse<List<Tag>>> {
        return repository.getAllTags()
    }

    fun getTagByPost(postId:String) : LiveData<GenericResponse<List<Tag>>> {
        return repository.getTagsByPost(postId)
    }

    fun tagPost(postId:String, tagName: String) : LiveData<GenericResponse<Tag>> {
        return repository.tagPost(postId, tagName)
    }
}
