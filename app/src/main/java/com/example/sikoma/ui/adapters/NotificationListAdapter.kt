package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Date
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Organization
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ItemDateSectionBinding
import com.example.sikoma.databinding.ItemPostBinding
import com.example.sikoma.databinding.ItemSearchOrganizationBinding

class NotificationListAdapter(private val items: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_POST = 1
        private const val VIEW_TYPE_DATE_SECTION = 2
    }

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class DateSectionViewHolder(val binding: ItemDateSectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Notification.PostItem -> VIEW_TYPE_POST
            is Notification.DateSectionItem -> VIEW_TYPE_DATE_SECTION
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_POST -> {
                val binding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                PostViewHolder(binding)
            }

            VIEW_TYPE_DATE_SECTION -> {
                val binding = ItemDateSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                DateSectionViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is Notification.DateSectionItem -> DateSectionBind(item, holder, position)
            is Notification.PostItem -> PostBind(item, holder, position)
        }
    }

    private fun PostBind(item: Notification.PostItem, holder: ViewHolder, position: Int) {
        val postHolder = holder as PostViewHolder

        postHolder.binding.apply {
            postAuthor.text = item.post.author
            postContent.text = item.post.content.toString()

            Glide.with(holder.itemView.context)
                .load(item.post.image)
                .placeholder(R.drawable.logo_app)
                .into(postImage)

            Glide.with(holder.itemView.context)
                .load(item.post.profilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(profilePic)
        }
    }

    private fun DateSectionBind(item: Notification.DateSectionItem, holder: RecyclerView.ViewHolder, position: Int) {
        val dateHolder = holder as DateSectionViewHolder

        dateHolder.binding.apply {
            dateLabel.text = item.date
        }
    }
    override fun getItemCount(): Int = items.size
}
