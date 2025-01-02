package com.example.sikoma.data.models

data class Topic (
    var topicName: String? = null
)

object TopicProvider{
    fun createDummy():List<Topic>{
        var topics = mutableListOf<Topic>()

        var names = listOf("Topic 1", "Topic 2", "Topic 3", "Topic 4")

        for(i in 0 until names.count()){

            topics.add(
                Topic(
                    topicName = names[i]
                )
            )
        }
        return topics
    }
}