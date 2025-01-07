package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Tag(

	@field:SerializedName("tag_name")
	val tagName: String? = null,

	@field:SerializedName("tag_id")
	val tagId: Int? = null
)
