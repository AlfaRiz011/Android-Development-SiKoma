package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("study_prog")
	val studyProg: String? = null,

	@field:SerializedName("faculty")
	val faculty: String? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,
)
