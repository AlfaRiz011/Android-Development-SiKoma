package com.example.sikoma.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.DateSection
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.PostItem
import com.example.sikoma.databinding.ItemDateSectionBinding
import com.example.sikoma.databinding.ItemPostBinding
import com.example.sikoma.databinding.ItemSearchOrganizationBinding
import com.example.sikoma.ui.activities.PostDetailActivity
import com.example.sikoma.ui.activities.ProfileOrganizationActivity

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
            is PostItem -> VIEW_TYPE_POST
            is DateSection -> VIEW_TYPE_DATE_SECTION
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
            is DateSection -> dateSectionBind(item, holder, position)
            is PostItem -> postBind(item, holder, position)
        }
    }

    private fun postBind(item: PostItem, holder: ViewHolder, position: Int) {
        val postHolder = holder as PostViewHolder

        postHolder.binding.apply {
            postAuthor.text = item.posts.admin?.organizationName
            postContent.text = item.posts.description

            Glide.with(holder.itemView.context)
                .load(item.posts.image)
                .placeholder(R.drawable.logo_app)
                .into(postImage)

            Glide.with(holder.itemView.context)
                .load(item.posts.admin?.profilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(profilePic)

            itemPost.setOnClickListener {
                val intent = Intent(root.context, PostDetailActivity::class.java).apply {
                    putExtra("postId", item.posts.postId.toString())
                }
                root.context.startActivity(intent)
            }

            fun openProfileActivity() {
                val intent = Intent(root.context, ProfileOrganizationActivity::class.java).apply {
                    putExtra("adminId", item.posts.adminId.toString())
                }
                root.context.startActivity(intent)
            }

            postAuthor.setOnClickListener { openProfileActivity() }
            profilePic.setOnClickListener { openProfileActivity() }
        }
    }

    private fun dateSectionBind(item: DateSection, holder: RecyclerView.ViewHolder, position: Int) {
        val dateHolder = holder as DateSectionViewHolder

        dateHolder.binding.apply {
            dateLabel.text = item.dateLabel
        }
    }
    override fun getItemCount(): Int = items.size
}
