package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ItemTopicBinding

class TopicPostAdapter (private val topics: List<Tag>) : RecyclerView.Adapter<TopicPostAdapter.TopicViewHolder>() {
    inner class TopicViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.binding.apply {
            topicLabel.text = topic.tagName
        }
    }
    override fun getItemCount(): Int = topics.size
}