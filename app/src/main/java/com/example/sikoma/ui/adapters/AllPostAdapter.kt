package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ItemPostBinding

class AllPostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<AllPostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.apply {
            postAuthor.text = post.author
            postContent.text = holder.itemView.context.getString(post.content)

            Glide.with(holder.itemView.context)
                .load(post.image)
                .placeholder(R.drawable.logo_app)
                .into(postImage)

            Glide.with(holder.itemView.context)
                .load(post.profilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(profilePic)
        }

    }

    override fun getItemCount(): Int = posts.size

}