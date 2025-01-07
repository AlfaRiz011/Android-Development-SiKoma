package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Like(

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null
)
