package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Otp(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("expires_at")
	val expiresAt: String? = null,

	@field:SerializedName("verified")
	val verified: Boolean? = null,

	@field:SerializedName("otp_code")
	val otpCode: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("otp_id")
	val otpId: Int? = null
)
