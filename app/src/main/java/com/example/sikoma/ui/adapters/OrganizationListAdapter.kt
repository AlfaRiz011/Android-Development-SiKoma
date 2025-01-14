package com.example.sikoma.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Admin
import com.example.sikoma.databinding.ItemSearchOrganizationBinding
import com.example.sikoma.ui.activities.ProfileOrganizationActivity

class OrganizationListAdapter (private val organizations: List<Admin>) : RecyclerView.Adapter<OrganizationListAdapter.OrganizationViewHolder>() {
    inner class OrganizationViewHolder(val binding: ItemSearchOrganizationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationViewHolder {
        val binding = ItemSearchOrganizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganizationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrganizationViewHolder, position: Int) {
        val org = organizations[position]
        holder.binding.apply {
            organizationLabel.text = org.organizationName
            organizationBio.text = org.bio

            Glide.with(holder.itemView.context)
                .load(org.profilePic)
                .placeholder(R.drawable.logo_app)
                .into(organizationPic)

            itemSearchOrganization.setOnClickListener {
                val intent = Intent(root.context, ProfileOrganizationActivity::class.java).apply {
                    putExtra("adminId", org.adminId)
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = organizations.size
}