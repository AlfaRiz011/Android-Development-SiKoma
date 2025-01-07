package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Admin(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("admin_id")
	val adminId: Int? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("organization_name")
	val organizationName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("posts_count")
	val postsCount: Int? = null
)
