package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ItemLookTopicBinding
import com.example.sikoma.databinding.ItemSearchTopicBinding
import com.example.sikoma.utils.OnTagClickListener

class TopicListAdapter(
    private val topics: List<Tag>,
    private val tagListener: (Tag) -> Unit
) : RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {
    inner class TopicViewHolder(val binding: ItemSearchTopicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding =
            ItemSearchTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.binding.topicLabel.text = topic.tagName
        holder.itemView.setOnClickListener { tagListener(topic) }

    }

    override fun getItemCount(): Int = topics.size
}
