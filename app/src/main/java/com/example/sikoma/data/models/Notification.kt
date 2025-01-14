package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Notification(

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("notif_id")
	val notifId: Int? = null,

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field: SerializedName("post")
	val post: Post,

	@field: SerializedName("user")
	val user: User
)
