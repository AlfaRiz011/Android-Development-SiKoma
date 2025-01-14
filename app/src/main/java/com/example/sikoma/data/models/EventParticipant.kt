package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class EventParticipant(

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("post")
	val post: Post,

	@field:SerializedName("user")
	val user: User
)
