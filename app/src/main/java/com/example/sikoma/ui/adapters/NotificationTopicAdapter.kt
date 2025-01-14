package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.FollowTag
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ItemNotificationListBinding

class NotificationTopicAdapter (private val topics: List<FollowTag>) : RecyclerView.Adapter<NotificationTopicAdapter.TopicViewHolder>() {
    inner class TopicViewHolder(val binding: ItemNotificationListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemNotificationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val tag = topics[position]
        holder.binding.apply {
            itemNotifList.text = tag.tag.tagName
        }
    }
    override fun getItemCount(): Int = topics.size
}