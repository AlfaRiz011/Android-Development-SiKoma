package com.example.sikoma.data.remote.config

import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.models.EventParticipant
import com.example.sikoma.data.models.FollowAdmin
import com.example.sikoma.data.models.FollowTag
import com.example.sikoma.data.models.Like
import com.example.sikoma.data.models.Login
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Otp
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.models.Tag
import com.example.sikoma.data.models.User
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.data.remote.response.GenericResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
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

    @GET("posts/event")
    fun getEventPost(): Call<GenericResponse<List<Post>>>

    @GET("posts/event/admin/{adminId}")
    fun getEventPostByAdmin(
        @Path("adminId") adminId: String
    ): Call<GenericResponse<List<Post>>>

    @POST("posts/{adminId}")
    @Multipart
    fun createPost(
        @Path("adminId") adminId: String,
        @Part("description") description: RequestBody,
        @Part("type") type: RequestBody,
        @Part("event_name") eventName: RequestBody?,
        @Part("event_date") eventDate: RequestBody?,
        @Part("event_time") eventTime: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Call<GenericResponse<Post>>

    @PUT("posts/{postId}")
    fun updatePost(
        @Path("adminId") adminId: String,
        @Part("description") description: RequestBody,
        @Part("type") type: RequestBody,
        @Part("event_name") eventName: RequestBody?,
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

    // FOLLOWING
    @POST("follow/tag/{userId}")
    fun followTag(
        @Path("userId") userId: String,
        @Query("tag_id") tagId: String
    ): Call<GenericResponse<FollowTag>>

    @POST("follow/admin/{userId}")
    fun followAdmin(
        @Path("userId") userId: String,
        @Query("admin_id") adminId: String
    ): Call<GenericResponse<FollowAdmin>>

    @DELETE("follow/unfollow/tag/{userId}")
    fun unfollowTag(
        @Path("userId") userId: String,
        @Query("tag_id") tagId: String
    ): Call<GenericResponse<FollowTag>>

    @DELETE("follow/unfollow/admin/{userId}")
    fun unfollowAdmin(
        @Path("userId") userId: String,
        @Query("admin_id") adminId: String
    ): Call<GenericResponse<FollowAdmin>>

    // EVENT PARTICIPATION
    @GET("event/{postId}")
    fun getParticipant(
        @Path("postId") postId: String
    ): Call<GenericResponse<List<EventParticipant>>>

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
    @POST("posts/like/{userId}")
    fun likePost(
        @Path("userId") userId: String,
        @Query("post_id") postId: String
    ): Call<GenericResponse<Like>>

    @DELETE("posts/unlike/{userId}")
    fun unlikePost(
        @Path("userId") userId: String,
        @Query("post_id") postId: String
    ): Call<GenericResponse<Like>>

    // NOTIFICATIONS
    @POST("notifications/{userId}")
    fun createNotification(
        @Path("userId") userId: String,
        @Query("post_id") postId: String
    ): Call<GenericResponse<Notification>>

    @PATCH("notifications/all/{userId}")
    fun updateAllNotifications(
        @Path("userId") userId: String,
    ): Call<GenericResponse<Notification>>

    @PATCH("notifications/one/{notifId}")
    fun updateOneNotifications(
        @Path("notifId") notifId: String,
    ): Call<GenericResponse<Notification>>

    @GET("notifications/info/{userId}")
    fun getUserNotificationPost(
        @Path("userId") userId: String
    ): Call<GenericResponse<List<Notification>>>

    @GET("notifications/event/{userId}")
    fun getUserNotificationEvent(
        @Path("userId") userId: String
    ): Call<GenericResponse<List<Notification>>>

    @GET("notifications/{notifId}")
    fun getNotifById(
        @Path("notifId") notifId: String
    ): Call<GenericResponse<Notification>>

    // REGISTER AND LOGIN
    @POST("register")
    fun register(
        @Body registerRequest: RegisterBodyRequest
    ): Call<GenericResponse<User>>

    @POST("register/request-otp")
    fun requestOtp(
        @Body otpRequest: OtpBodyRequest
    ): Call<GenericResponse<Otp>>

    @POST("register/verify-otp")
    fun verifyOtp(
        @Body verifyOtpRequest: OtpBodyRequest
    ): Call<GenericResponse<Register>>

    @POST("login")
    fun login(
        @Body loginRequest: LoginBodyRequest
    ): Call<GenericResponse<Login>>
}
