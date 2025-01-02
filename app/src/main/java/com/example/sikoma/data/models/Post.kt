package com.example.sikoma.data.models

import com.example.sikoma.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

data class Post(
    val title: String,
    val content: Int,
    val image: Int,
    val author: String,
    val profilePic: Int,
    val postDate: String
)


object PostProvider {
    fun createDummy(postNumber: Int): List<Post> {
        val posts = mutableListOf<Post>()
        val names = listOf("Multimedia", "Android", "Cyber Security", "Robotics")

        val startDate = "2024-01-01"
        val endDate = "2024-12-31"

        for (i in 1..postNumber) {
            val randomIndex = names.indices.random()
            val randomPostDate = randomDate(startDate, endDate)

            posts.add(
                Post(
                    title = "Post $i",
                    content = R.string.lorem_ipsum_1,
                    image = R.drawable.logo_upn,
                    author = names[randomIndex],
                    profilePic = R.drawable.icon_profile_fill,
                    postDate = randomPostDate
                )
            )
        }

        return posts
    }

    private fun randomDate(
        startDate: String,
        endDate: String
    ): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = dateFormat.parse(startDate)?.time ?: 0L
        val end = dateFormat.parse(endDate)?.time ?: 0L
        val randomTime = Random.nextLong(start, end)

        return dateFormat.format(Date(randomTime))
    }
}
