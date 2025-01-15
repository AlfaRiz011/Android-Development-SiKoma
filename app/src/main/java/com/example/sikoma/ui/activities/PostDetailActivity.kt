package com.example.sikoma.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ActivityPostDetailBinding
import com.example.sikoma.ui.adapters.TopicPostAdapter
import com.example.sikoma.ui.viewmodels.EventViewModel
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.TagViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var adapter: TopicPostAdapter
    private lateinit var postId: String
    private lateinit var role: String
    private lateinit var userId: String
    private lateinit var adminId: String

    private val viewModel: PostViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val tagViewModel: TagViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val eventViewModel : EventViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val pref = viewModel.preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra("postId") ?: ""

        lifecycleScope.launch {
            role = pref.getRole().first() ?: ""
            userId = pref.getUser().firstOrNull()?.userId.toString()
            adminId = pref.getAdmin().firstOrNull()?.adminId.toString()
        }

        setLoading()
        setView()
        setAdapter()
    }

    private fun setLoading() {
        viewModel.isLoading.observe(this@PostDetailActivity) {
            showLoading(it)
        }

        tagViewModel.isLoading.observe(this@PostDetailActivity) {
            showLoading(it)
        }

        eventViewModel.isLoading.observe(this@PostDetailActivity) {
            showLoading(it)
        }
    }

    private fun setAdapter() {
        tagViewModel.getTagByPost(postId).observe(this) {
            when {
                it.status == "success" -> {
                    val topic = it.data

                    if (topic != null) {
                        adapter = TopicPostAdapter(topic)

                        val layoutManager = FlexboxLayoutManager(this@PostDetailActivity)
                        layoutManager.flexDirection = FlexDirection.ROW
                        layoutManager.justifyContent = JustifyContent.CENTER
                        binding.topicRv.layoutManager = layoutManager
                        binding.topicRv.overScrollMode = View.OVER_SCROLL_NEVER
                        binding.topicRv.adapter = adapter
                    }
                }

                else -> handleError(it.message?.toInt())
            }
        }

    }

    private fun setView() {
        viewModel.getPostById(postId).observe(this) { response ->
            when (response.status) {
                "success" -> response.data?.let { post ->
                    setupPostDetails(post)
                    setupEventButtons(post)
                }
                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun setupPostDetails(post: Post) {
        binding.apply {
            postAuthor.text = post.admin?.organizationName.orEmpty()
            postContent.text = post.description.orEmpty()

            includeSchedule.date.text = post.eventDate
            includeSchedule.time.text = post.eventTime
            includeSchedule.location.text = post.eventLocation

            loadImage(this@PostDetailActivity, post.admin?.profilePic, profilePic, R.drawable.icon_profile_fill)
            loadImage(this@PostDetailActivity, post.image, postImage, R.drawable.logo_app)
        }
    }

    private fun setupEventButtons(post: Post) {
        binding.apply {
            when {
                role == "admin" && post.adminId.toString() == adminId -> setButtonVisibility(buttonExport = true)
                role == "user" && post.type == "event" -> {
                    setButtonVisibility(buttonSchedule = true, buttonJoinEvent = true)

                    buttonJoinEvent.setOnClickListener {
                        handleJoinEvent(postId)
                    }

                    buttonCancelEvent.setOnClickListener {
                        handleCancelEvent(postId)
                    }

                    buttonSchedule.setOnClickListener {
                        includeSchedule.layoutSchedue.visibility = View.VISIBLE
                    }

                    includeSchedule.backButtonSchedule.setOnClickListener {
                        includeSchedule.layoutSchedue.visibility = View.GONE
                    }
                }
                else -> setButtonVisibility()
            }
        }
    }

    private fun setButtonVisibility(
        buttonExport: Boolean = false,
        buttonSchedule: Boolean = false,
        buttonJoinEvent: Boolean = false,
        buttonCancelEvent: Boolean = false
    ) {
        binding.apply {
            this.buttonExport.visibility = if (buttonExport) View.VISIBLE else View.GONE
            this.buttonSchedule.visibility = if (buttonSchedule) View.VISIBLE else View.GONE
            this.buttonJoinEvent.visibility = if (buttonJoinEvent) View.VISIBLE else View.GONE
            this.buttonCancelEvent.visibility = if (buttonCancelEvent) View.VISIBLE else View.GONE
        }
    }

    private fun handleJoinEvent(postId: String) {
        eventViewModel.participate(postId, userId).observe(this@PostDetailActivity) { event ->
            when (event.status) {
                "success" -> setButtonVisibility(buttonCancelEvent = true)
                else -> handleError(event.message?.toInt())
            }
        }
    }

    private fun handleCancelEvent(postId: String) {
        eventViewModel.deleteParticipant(postId, userId).observe(this@PostDetailActivity) { event ->
            when (event.status) {
                "success" -> setButtonVisibility(buttonJoinEvent = true)
                else -> handleError(event.message?.toInt())
            }
        }
    }

    private fun loadImage(context: Context, url: String?, imageView: ImageView, placeholder: Int) {
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)
    }


    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                this@PostDetailActivity,
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                this@PostDetailActivity,
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                this@PostDetailActivity,
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                this@PostDetailActivity,
                getString(R.string.error_server_500)
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}