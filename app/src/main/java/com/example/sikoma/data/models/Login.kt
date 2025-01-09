package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName


data class Login(
    @SerializedName("token")
    val token: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("user")
    val user: User? = null,

    @SerializedName("admin")
    val admin: Admin? = null
)
