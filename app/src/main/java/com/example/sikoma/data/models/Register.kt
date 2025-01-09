package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName

data class Register(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

)

