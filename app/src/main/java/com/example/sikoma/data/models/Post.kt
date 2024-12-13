package com.example.sikoma.data.models

import com.example.sikoma.R

data class Post(
    val title: String,
    val content: Int,
    val image: Int,
    val author: String,
    val profilePic: Int
)


object PostProvider {
    fun createDummy(postNumber: Int): List<Post> {
        val posts = mutableListOf<Post>()

        val names = listOf("Multimedia", "Android", "Cyber Security", "Robotics")

        for (i in 1..postNumber) {
            val randomIndex = (names.indices).random() // Pick random name and avatar
            posts.add(
                Post(
                    title = "Post $i",
                    content = R.string.lorem_ipsum_1,
                    image = R.drawable.logo_upn,
                    author = names[randomIndex],
                    profilePic = R.drawable.icon_profile_fill
                )
            )
        }

        return posts
    }
}
