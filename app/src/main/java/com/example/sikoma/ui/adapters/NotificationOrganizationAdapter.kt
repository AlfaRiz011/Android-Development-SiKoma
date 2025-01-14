package com.example.sikoma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.models.FollowAdmin
import com.example.sikoma.databinding.ItemNotificationListBinding

class NotificationOrganizationAdapter (private val admin: List<FollowAdmin>) : RecyclerView.Adapter<NotificationOrganizationAdapter.AdminViewHolder>() {
    inner class AdminViewHolder(val binding: ItemNotificationListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val binding = ItemNotificationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val org = admin[position]
        holder.binding.apply {
            itemNotifList.text = org.admin.organizationName
        }
    }
    override fun getItemCount(): Int = admin.size
}