import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ItemPostBinding
import com.example.sikoma.ui.activities.PostDetailActivity
import com.example.sikoma.ui.activities.ProfileOrganizationActivity

class MyEventAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<MyEventAdapter.PostViewHolder>() {
    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.apply {
            postAuthor.text = post.admin?.organizationName ?: "Unknown Organization"
            postContent.text = post.description

            Glide.with(holder.itemView.context)
                .load(post.image)
                .placeholder(R.drawable.logo_app)
                .into(postImage)

            post.admin?.profilePic?.let {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .placeholder(R.drawable.icon_profile_fill)
                    .into(profilePic)
            }

            itemPost.setOnClickListener {
                val intent = Intent(root.context, PostDetailActivity::class.java).apply {
                    putExtra("postId", post.postId.toString())
                }
                root.context.startActivity(intent)
            }

            val openProfileActivity = {
                val intent = Intent(root.context, ProfileOrganizationActivity::class.java).apply {
                    putExtra("adminId", post.adminId?.toString() ?: "")
                }
                root.context.startActivity(intent)
            }

            postAuthor.setOnClickListener { openProfileActivity() }
            profilePic.setOnClickListener { openProfileActivity() }
        }
    }

    override fun getItemCount(): Int = posts.size
}
