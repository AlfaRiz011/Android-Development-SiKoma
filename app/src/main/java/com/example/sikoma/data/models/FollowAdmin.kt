package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class FollowAdmin(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("admin_id")
	val adminId: Int? = null,

	@field:SerializedName("admin")
	val admin: Admin
)
