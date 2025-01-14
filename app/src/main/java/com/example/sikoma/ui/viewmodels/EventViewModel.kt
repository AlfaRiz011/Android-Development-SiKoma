package com.example.sikoma.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.EventParticipant
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.remote.response.GenericResponse
import com.example.sikoma.data.repository.EventRepository

class EventViewModel(
    private val repository: EventRepository,
    private val pref: UserPreferences
): ViewModel() {
    var preferences = pref
    var isLoading = repository.isLoading

    fun getParticipant(postId: String): LiveData<GenericResponse<List<EventParticipant>>> {
        return repository.getParticipant(postId)
    }

    fun getParticipantUserId(userId: String): LiveData<GenericResponse<List<Post>>> {
        return repository.getParticipantUserId(userId)
    }

    fun participate(postId: String, userId : String): LiveData<GenericResponse<EventParticipant>> {
        return repository.participateEvent(postId, userId)
    }

    fun deleteParticipant(postId: String, userId : String): LiveData<GenericResponse<EventParticipant>> {
        return repository.deleteParticipant(postId, userId)
    }
}
