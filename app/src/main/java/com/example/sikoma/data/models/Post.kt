package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Post(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("admin_id")
	val adminId: Int? = null,

	@field:SerializedName("event_date")
	val eventDate: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("event_location")
	val eventLocation: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("event_time")
	val eventTime: String? = null,

	@field: SerializedName("Admin")
	val admin: Admin? = null
)
