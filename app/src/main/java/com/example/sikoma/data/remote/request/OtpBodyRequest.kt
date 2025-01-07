package com.example.sikoma.data.remote.request

import com.google.gson.annotations.SerializedName

data class OtpBodyRequest(

	@field:SerializedName("otp")
	val otp: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
