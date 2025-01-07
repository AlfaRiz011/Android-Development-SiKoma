package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class PostTag(

	@field:SerializedName("post_id")
	val postId: Int? = null,

	@field:SerializedName("tag_id")
	val tagId: Int? = null
)
