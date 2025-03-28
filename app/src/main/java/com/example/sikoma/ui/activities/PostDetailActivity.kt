package com.example.sikoma.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ActivityPostDetailBinding
import com.example.sikoma.ui.adapters.TopicPostAdapter
import com.example.sikoma.ui.viewmodels.EventViewModel
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.TagViewModel
import com.example.sikoma.ui.viewmodels.factory.EventViewModelFactory
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.ui.viewmodels.factory.TagViewModelFactory
import com.example.sikoma.utils.ExportHelper
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var adapter: TopicPostAdapter
    private lateinit var postId: String
    private lateinit var role: String
    private lateinit var userId: String
    private lateinit var adminId: String

    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory.getInstance(this)
    }

    private val tagViewModel: TagViewModel by viewModels {
        TagViewModelFactory.getInstance(this)
    }

    private val eventViewModel: EventViewModel by viewModels {
        EventViewModelFactory.getInstance(this)
    }

    private lateinit var pref: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra("postId") ?: ""

        pref = viewModel.preferences

        lifecycleScope.launch {
            role = pref.getRole().first() ?: ""
            userId = pref.getUser().firstOrNull()?.userId.toString()
            adminId = pref.getAdmin().firstOrNull()?.adminId.toString()

            setAppBar()
            setLoading()

            withContext(Dispatchers.Main) {
                setView()
                setAdapter()
            }
        }
    }

    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)
        binding.buttonBack.setOnClickListener { finish() }

        supportActionBar?.setDisplayShowTitleEnabled(false)
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
                    val isEvent = post.type == "event"
                    val isUser = role == "user"
                    val isAdmin = role == "admin"
                    val isOwnEvent = post.adminId.toString() == adminId

                    setupLike()
                    setupPostDetails(post)

                    if (isUser && isEvent) {
                        setupEventButtons(post)
                    }

                    if (isAdmin) {
                        binding.buttonLike.visibility = View.GONE

                        if (isEvent && isOwnEvent) {
                            setupAdminEventView()
                        }
                    }
                }

                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun setupPostDetails(post: Post) {
        binding.apply {
            buttonCancelEvent.visibility = View.GONE
            buttonJoinEvent.visibility = View.GONE
            buttonSchedule.visibility = View.GONE
            buttonExport.visibility = View.GONE
            confirmJoin.layoutConfirmJoin.visibility = View.GONE
            cancelJoin.layoutCancelJoin.visibility = View.GONE

            postAuthor.text = post.admin?.organizationName.orEmpty()
            postContent.text = post.description.orEmpty()

            includeSchedule.date.text = formatDate(post.eventDate)
            includeSchedule.time.text = formatTime(post.eventTime)
            includeSchedule.location.text = post.eventLocation.orEmpty()

            loadImage(
                this@PostDetailActivity,
                post.admin?.profilePic,
                profilePic,
                R.drawable.icon_profile_fill
            )
            loadImage(this@PostDetailActivity, post.image, postImage, R.drawable.logo_app)
        }
    }

    private fun formatDate(dateTime: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateTime ?: return "-")
            outputFormat.format(date ?: return "-")
        } catch (e: Exception) {
            Toast.makeText(this@PostDetailActivity, "Format tanggal tidak valid: ${e.message}", Toast.LENGTH_SHORT).show()
            "-"
        }
    }

    private fun formatTime(time: String?): String {
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return try {
            val date = inputFormat.parse(time ?: return "-")
            outputFormat.format(date ?: return "-")
        } catch (e: Exception) {
            "-"
        }
    }

    private fun setupEventButtons(post: Post) {
        binding.buttonSchedule.visibility = View.VISIBLE

        eventViewModel.getParticipantUserId(userId).observe(this) { response ->
            when (response.status) {
                "success" -> {
                    response.data?.let { responsePosts ->
                        val isParticipant = post.postId != null &&
                                responsePosts.any { it.postId == post.postId }

                        if (isParticipant) {
                            binding.buttonCancelEvent.visibility = View.VISIBLE
                            binding.buttonJoinEvent.visibility = View.GONE
                        } else {
                            binding.buttonCancelEvent.visibility = View.GONE
                            binding.buttonJoinEvent.visibility = View.VISIBLE
                        }
                    } ?: run {
                        binding.buttonCancelEvent.visibility = View.GONE
                        binding.buttonJoinEvent.visibility = View.VISIBLE
                    }
                }

                else -> handleError(response.message?.toInt())
            }

            binding.buttonSchedule.setOnClickListener {
                binding.includeSchedule.layoutSchedue.visibility = View.VISIBLE
            }

            binding.includeSchedule.backButtonSchedule.setOnClickListener {
                binding.includeSchedule.layoutSchedue.visibility = View.GONE
            }

            binding.buttonJoinEvent.setOnClickListener {
                binding.confirmJoin.layoutConfirmJoin.visibility = View.VISIBLE

                binding.confirmJoin.buttonConfirm.setOnClickListener {
                    joinEvent()
                }
            }

            binding.buttonCancelEvent.setOnClickListener {
                binding.cancelJoin.layoutCancelJoin.visibility = View.VISIBLE

                binding.cancelJoin.buttonCancel.setOnClickListener {
                    cancelEvent()
                }
            }
        }
    }

    private fun setupAdminEventView() {
        binding.apply {
            buttonSchedule.visibility = View.VISIBLE
            buttonExport.visibility = View.VISIBLE

            binding.buttonExport.setOnClickListener {
                exportDataParticipant()
            }

            binding.buttonSchedule.setOnClickListener {
                binding.includeSchedule.layoutSchedue.visibility = View.VISIBLE
            }

            binding.includeSchedule.backButtonSchedule.setOnClickListener {
                binding.includeSchedule.layoutSchedue.visibility = View.GONE
            }
        }
    }

    private fun exportDataParticipant() {
        eventViewModel.getParticipant(postId).observe(this@PostDetailActivity) { response ->
            when (response.status) {
                "success" ->{
                    response.data?.let {
                        ExportHelper.exportUserData(
                            context = this,
                            participants = it,
                            directory = getExternalFilesDir(null) ?: filesDir
                        )
                    }
                }
                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun joinEvent() {
        eventViewModel.participate(postId, userId)
            .observe(this@PostDetailActivity) { response ->
                when (response.status) {
                    "success" -> refreshActivity()
                    else -> handleError(response.message?.toInt())
                }
            }
    }

    private fun cancelEvent() {
        eventViewModel.deleteParticipant(postId, userId).observe(this) { response ->
            when (response.status) {
                "success" -> refreshActivity()
                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun setupLike() {
        viewModel.getLikePost(postId).observe(this) { response ->
            when (response.status) {
                "success" -> {
                    response.data?.let { responsePosts ->
                        val likeCount = responsePosts.count()

                        binding.buttonLike.text = likeCount.toString()

                        val liked = responsePosts.any { it.postId.toString() == postId }
                        updateLikeButtonUI(liked)

                        binding.buttonLike.setOnClickListener {
                            toggleLike(liked)
                        }
                    }
                }

                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun toggleLike(liked: Boolean) {
        viewModel.toggleLike(userId, postId).observe(this) { response ->
            when (response.status) {
                "success" -> {
                    updateLikeButtonUI(!liked)
                }

                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun updateLikeButtonUI(liked: Boolean) {
        binding.buttonLike.setIconResource(
            if (liked) R.drawable.icon_like_fill else R.drawable.icon_like
        )
    }

    private fun loadImage(
        context: Context,
        url: String?,
        imageView: ImageView,
        placeholder: Int
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)
    }

    private fun refreshActivity() {
        val refreshIntent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra("postId", postId)
        }
        finish()
        startActivity(refreshIntent)
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
        val visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loadingView.visibility = visibility
        binding.progressBar.visibility = visibility
    }
}