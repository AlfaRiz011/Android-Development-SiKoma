package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ItemTopicBinding

class AddTopicAdapter(private var topics: MutableList<Tag>) : RecyclerView.Adapter<AddTopicAdapter.TopicViewHolder>() {

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

    // Fungsi untuk menambahkan item baru ke dalam list dan memperbarui RecyclerView
    fun addTag(newTag: Tag) {
        topics.add(newTag) // Menambahkan item ke daftar
        notifyItemInserted(topics.size - 1) // Memperbarui RecyclerView hanya pada item yang baru ditambahkan
    }
}
