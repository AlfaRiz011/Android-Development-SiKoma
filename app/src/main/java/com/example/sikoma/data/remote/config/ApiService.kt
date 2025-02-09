package com.example.sikoma.data.remote.config

import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.models.EventParticipant
import com.example.sikoma.data.models.Like
import com.example.sikoma.data.models.Login
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.models.Tag
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.remote.response.GenericResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // USERS
    @GET("users")
    fun getAllUser(): Call<GenericResponse<List<User>>>

    @GET("users/{id}")
    fun getUserById(
        @Path("id") id: String
    ): Call<GenericResponse<User>>

    @GET("users")
    fun getUserByEmail(
        @Query("email") email: String
    ): Call<GenericResponse<User>>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: String,
        @Body updateUserRequest: User
    ): Call<GenericResponse<User>>

    @PUT("users/{id}/profile-pic")
    fun updateUserProfilePic(
        @Path("id") id: String,
        @Part image: MultipartBody.Part?
    ): Call<GenericResponse<User>>


    // ADMINS
    @GET("admins")
    fun getAllAdmins(): Call<GenericResponse<List<Admin>>>

    @GET("admins/find")
    fun getAdminByName(
        @Query("organization_name") organizationName: String
    ): Call<GenericResponse<Admin>>

    @GET("admins/{id}")
    fun getAdminById(
        @Path("id") id: String
    ): Call<GenericResponse<Admin>>

    @PUT("admins/{id}")
    fun updateAdmin(
        @Path("id") id: String,
        @Body updateAdminRequest: Admin
    ): Call<GenericResponse<Admin>>

    @PUT("admins/{id}/profile-pic")
    fun updateAdminProfilePic(
        @Path("id") id: String,
        @Part image: MultipartBody.Part?
    ): Call<GenericResponse<Admin>>

    // POSTS
    @GET("posts")
    fun getAllPost(): Call<GenericResponse<List<Post>>>

    @GET("posts/{postId}")
    fun getPostById(
        @Path("postId") postId: String
    ): Call<GenericResponse<Post>>

    @GET("posts/admin/{adminId}")
    fun getPostByAdmin(
        @Path("adminId") adminId: String
    ): Call<GenericResponse<List<Post>>>

    @GET("posts/recommendation/{id}")
    fun getPostRecommendation(
        @Path("id") userId: String
    ): Call<GenericResponse<List<Post>>>

    @GET("posts/event/events")
    fun getEventPost(): Call<GenericResponse<List<Post>>>

    @GET("posts/event/admin/{adminId}")
    fun getEventPostByAdmin(
        @Path("adminId") adminId: String
    ): Call<GenericResponse<List<Post>>>

    @POST("posts/{adminId}")
    @Multipart
    fun createPost(
        @Path("adminId") adminId: String,
        @Part("admin_id") adminBody: RequestBody,
        @Part("description") description: RequestBody,
        @Part("type") type: RequestBody,
        @Part("event_location") eventLocation: RequestBody?,
        @Part("event_date") eventDate: RequestBody?,
        @Part("event_time") eventTime: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<GenericResponse<Post>>

    @PUT("posts/{postId}")
    fun updatePost(
        @Path("adminId") adminId: String,
        @Part("admin_id") adminBody: RequestBody,
        @Part("description") description: RequestBody,
        @Part("type") type: RequestBody,
        @Part("event_location") eventLocation: RequestBody?,
        @Part("event_date") eventDate: RequestBody?,
        @Part("event_time") eventTime: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<GenericResponse<Post>>

    @DELETE("posts/{postId}")
    fun deletePost(
        @Path("postId") postId: String
    ): Call<GenericResponse<Post>>


    // TAGS
    @GET("tags")
    fun getAllTags(): Call<GenericResponse<List<Tag>>>

    @GET("tags/{postId}")
    fun getTagsByPost(
        @Path("postId") postId: String
    ): Call<GenericResponse<List<Tag>>>

    @POST("tags/{postId}")
    fun tagPost(
        @Path("postId") postId: String,
        @Query("tag_name") tagName: String
    ): Call<GenericResponse<Tag>>

    // EVENT PARTICIPATION
    @GET("event/participant/{postId}")
    fun getParticipant(
        @Path("postId") postId: String
    ): Call<GenericResponse<List<EventParticipant>>>

    @GET("event/post/{userId}")
    fun getParticipantUserId(
        @Path("userId") userId: String
    ): Call<GenericResponse<List<Post>>>

    @POST("event/{postId}")
    fun participateEvent(
        @Path("postId") postId: String,
        @Query("user_id") userId: String
    ): Call<GenericResponse<EventParticipant>>

    @DELETE("event/{postId}")
    fun deleteParticipant(
        @Path("postId") postId: String,
        @Query("user_id") userId: String
    ): Call<GenericResponse<EventParticipant>>

    // LIKES
    @POST("posts/toggleLike/{userId}")
    fun toggleLike(
        @Path("userId") userId: String,
        @Query("post_id") postId: String
    ): Call<GenericResponse<Like>>

    @GET("posts/like/{postId}")
    fun getLikePost(
        @Path("post_id") postId: String
    ): Call<GenericResponse<List<Like>>>

    // REGISTER AND LOGIN
    @POST("register")
    fun register(
        @Body registerRequest: RegisterBodyRequest
    ): Call<GenericResponse<User>>

    @POST("register/check")
    fun checkUser(
        @Body checkRequest: Register
    ): Call<GenericResponse<Register>>

    @POST("login/")
    fun login(
        @Body loginRequest: LoginBodyRequest
    ): Call<GenericResponse<Login>>
}
