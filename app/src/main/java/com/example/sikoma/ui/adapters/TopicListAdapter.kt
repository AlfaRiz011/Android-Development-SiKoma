package com.example.sikoma.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ItemSearchTopicBinding
import com.example.sikoma.ui.activities.SearchViewActivity

class TopicListAdapter(
    private val topics: List<Tag>
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

        holder.binding.itemSearchTopic.setOnClickListener{
            val intent = Intent(holder.binding.root.context, SearchViewActivity::class.java).apply {
                putExtra("tagName", topic.tagName.toString())
            }
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = topics.size
}
