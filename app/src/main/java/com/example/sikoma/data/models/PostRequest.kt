package com.example.sikoma.data.models

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostRequest (
    @field:SerializedName("image")
    val image: MultipartBody.Part? = null,

    @field:SerializedName("admin_id")
    val adminId: String,

    @field:SerializedName("admin_body")
    val adminBody: RequestBody,

    @field:SerializedName("event_date")
    val eventDate: RequestBody? = null,

    @field:SerializedName("description")
    val description: RequestBody,

    @field:SerializedName("event_location")
    val eventLocation: RequestBody? = null,

    @field:SerializedName("type")
    val type: RequestBody,

    @field:SerializedName("event_time")
    val eventTime: RequestBody? = null,
)