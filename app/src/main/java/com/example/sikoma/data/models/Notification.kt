package com.example.sikoma.data.models


sealed class Notification(){
    data class PostItem(val post: Post) : Notification()
    data class DateSectionItem(val date: String) : Notification()
}

data class Date(
    val title: String
)