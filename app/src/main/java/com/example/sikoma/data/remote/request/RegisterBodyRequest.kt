package com.example.sikoma.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterBodyRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("study_prog")
	val studyProg: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("profile_pic")
	val profilePic: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("faculty")
	val faculty: String? = null
)
