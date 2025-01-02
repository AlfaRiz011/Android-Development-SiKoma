package com.example.sikoma.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Topic
import com.example.sikoma.databinding.ActivityPostDetailBinding
import com.example.sikoma.ui.adapters.TopicPostAdapter

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostDetailBinding
    private lateinit var adapter : TopicPostAdapter

    private lateinit var author: String
    private lateinit var content: String
    private var imageContent: Int? = null
    private var profilePic : Int? = null
    private lateinit var topic : List<Topic>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val color = ContextCompat.getColor(this, R.color.blueSecondary)
            v.setBackgroundColor(color)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        author = intent.getStringExtra("author") ?: "Unknown"
        content = intent.getStringExtra("content") ?: "Unkonwn"
        imageContent = intent.getIntExtra("image", R.drawable.logo_upn)
        profilePic = intent.getIntExtra("profilePic", R.drawable.icon_profile_fill)

        topic = intent.getSerializableExtra("topics") as? List<Topic> ?: emptyList()
        setView()
        setAdapter()
    }

    private fun setAdapter() {
        adapter = TopicPostAdapter(topic)

        val layoutManager = LinearLayoutManager(this@PostDetailActivity)
        binding.topicRv.layoutManager = layoutManager
        binding.topicRv.adapter = adapter
    }

    private fun setView() {
        binding.apply {
            postAuthor.text = author
            postContent.text = content

            Glide.with(this@PostDetailActivity)
                .load(profilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(binding.profilePic)

            Glide.with(this@PostDetailActivity)
                .load(imageContent)
                .placeholder(R.drawable.logo_app)
                .into(binding.postImage)
        }
    }
}