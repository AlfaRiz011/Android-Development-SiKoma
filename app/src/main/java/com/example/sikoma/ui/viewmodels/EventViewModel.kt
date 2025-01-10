package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.repository.EventRepository

class EventViewModel(
    private val repository: EventRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getParticipant(postId: String) = repository.getParticipant(postId)

    fun participate(postId: String, userId : String) = repository.participateEvent(postId, userId)

    fun deleteParticipant(postId: String, userId : String) = repository.deleteParticipant(postId, userId)
}
