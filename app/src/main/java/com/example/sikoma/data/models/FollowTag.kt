package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class FollowTag(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("tag_id")
	val tagId: Int? = null,

	@field:SerializedName("tag")
	val tag: Tag
)
