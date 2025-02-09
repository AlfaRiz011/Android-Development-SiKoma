package com.example.sikoma.ui.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.PostRequest
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.ActivityAddPostBinding
import com.example.sikoma.ui.adapters.TopicPostAdapter
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.TagViewModel
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.ui.viewmodels.factory.TagViewModelFactory
import com.example.sikoma.utils.CameraHelper.uriToFile
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    private lateinit var adapter: TopicPostAdapter
    private lateinit var tagList: List<Tag>
    private lateinit var adminId: String
    private var imageUri: Uri? = null
    private lateinit var postRequest: PostRequest

    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory.getInstance(this)
    }

    private val tagViewModel: TagViewModel by viewModels {
        TagViewModelFactory.getInstance(this)
    }

    private lateinit var pref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = viewModel.preferences

        lifecycleScope.launch {
            adminId = pref.getAdmin().firstOrNull()?.adminId.toString()
            setAppBar()
            setAction()

            withContext(Dispatchers.Main) {
                setView()
            }
        }

    }

    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonDone.setOnClickListener { upload() }

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun upload() {
        if (!validateInputs()) return
        postRequest = createPostRequest()

        viewModel.addPost(postRequest).observe(this@AddPostActivity) { response ->
            when (response.status) {
                "success" -> {
                    response.data?.let {
                        if (tagList.isNotEmpty()) {
                            uploadTagPost(response.data.postId.toString())
                        } else {

                            ValidatorAuthHelper.showToast(
                                this@AddPostActivity,
                                getString(R.string.uploaded_success)
                            )
                            navigateToHome()
                        }
                    }
                }

                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun uploadTagPost(postId: String) {
        for (tag in tagList) {
            tag.tagName?.let {
                tagViewModel.tagPost(postId, it).observe(this@AddPostActivity) { response ->
                    when (response.status) {
                        "success" -> {
                            ValidatorAuthHelper.showToast(
                                this@AddPostActivity,
                                getString(R.string.uploaded_success)
                            )

                            navigateToHome()
                        }
                        else -> handleError(response.message?.toInt())
                    }
                }
            }
        }
    }

    private fun setView() {
        binding.apply {
            locationInputLayout.visibility = View.GONE
            dateInputLayout.visibility = View.GONE
            timeInputLayout.visibility = View.GONE
            tagInputLayout.visibility = View.GONE
            buttonDoneAddTag.visibility = View.GONE
            topicRv.visibility = View.GONE
            switchOption.isChecked = false
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setAction() {
        binding.apply {
            switchOption.setOnCheckedChangeListener { _, isChecked ->
                val visibility = if (isChecked) View.VISIBLE else View.GONE
                locationInputLayout.visibility = visibility
                dateInputLayout.visibility = visibility
                timeInputLayout.visibility = visibility
            }

            inputDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    this@AddPostActivity,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val monthName = SimpleDateFormat(
                            "MMMM",
                            Locale.getDefault()
                        ).format(Calendar.getInstance().apply {
                            set(Calendar.MONTH, selectedMonth)
                        }.time)

                        val formattedDate =
                            String.format("%02d %s %d", selectedDay, monthName, selectedYear)
                        inputDate.setText(formattedDate)
                    },
                    year,
                    month,
                    day
                )

                datePicker.show()
            }

            inputTime.setOnClickListener {
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val timePicker =
                    TimePickerDialog(this@AddPostActivity, { _, selectedHour, selectedMinute ->
                        val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                        inputTime.setText(formattedTime)
                    }, hour, minute, true)

                timePicker.show()
            }

            buttonAddTag.setOnClickListener { addTag() }

            buttonChooseImage.setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }

            if (imageUri != null) {
                imageContainer.setOnClickListener {
                    imageUri = null
                    buttonChooseImage.visibility = View.VISIBLE

                    Glide.with(this@AddPostActivity)
                        .load(R.drawable.gradient)
                        .placeholder(R.drawable.gradient)
                        .into(binding.imageContainer)
                }
            }
        }
    }

    private fun addTag() {
        binding.apply {
            buttonAddTag.visibility = View.GONE
            tagInputLayout.visibility = View.VISIBLE
            buttonDoneAddTag.visibility = View.VISIBLE

            buttonDoneAddTag.setOnClickListener {
                val tagText = inputTag.text.toString().trim()
                if (tagText.isNotEmpty()) {
                    val newTag = Tag(tagId = null, tagName = tagText)
                    tagList = tagList + newTag
                    setAdapter(tagList)
                    inputTag.text?.clear()
                }

                buttonAddTag.visibility = View.VISIBLE
                tagInputLayout.visibility = View.GONE
                buttonDoneAddTag.visibility = View.GONE
            }
        }
    }

    private fun setAdapter(listTag: List<Tag>) {
        adapter = TopicPostAdapter(listTag)

        val layoutManager = FlexboxLayoutManager(this@AddPostActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.topicRv.layoutManager = layoutManager
        binding.topicRv.adapter = adapter
    }


    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it

                Glide.with(this@AddPostActivity)
                    .load(it)
                    .placeholder(R.drawable.gradient)
                    .into(binding.imageContainer)

                binding.buttonChooseImage.visibility = View.GONE
            }
        }

    private fun createPostRequest(): PostRequest {
        val imageFile = imageUri?.let { uriToFile(it, this) }
        val requestBody = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart =
            requestBody?.let { MultipartBody.Part.createFormData("image", imageFile.name, it) }

        val description =
            binding.inputCaption.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val type = if (binding.switchOption.isChecked) {
            "event".toRequestBody("text/plain".toMediaTypeOrNull())
        } else {
            " ".toRequestBody("text/plain".toMediaTypeOrNull())
        }
        val eventDate = if (binding.switchOption.isChecked) {
            binding.inputDate.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        } else null
        val eventTime = if (binding.switchOption.isChecked) {
            binding.inputTime.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        } else null

        val eventLocation = binding.inputLocation.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        return PostRequest(
            image = imagePart,
            adminId = adminId,
            adminBody = adminId.toRequestBody("text/plain".toMediaTypeOrNull()),
            eventDate = eventDate,
            description = description,
            eventLocation = eventLocation,
            type = type,
            eventTime = eventTime
        )
    }

    private fun validateInputs(): Boolean {
        with(binding) {
            if (inputCaption.text.isNullOrEmpty()) {
                ValidatorAuthHelper.showToast(
                    this@AddPostActivity,
                    "Caption tidak boleh kosong"
                )
                return false
            }

            if(imageUri.toString().isEmpty()){
                ValidatorAuthHelper.showToast(
                    this@AddPostActivity,
                    "Image tidak boleh kosong"
                )
                return false
            }

            if (switchOption.isChecked) {
                if (inputLocation.text.isNullOrEmpty()) {
                    ValidatorAuthHelper.showToast(
                        this@AddPostActivity,
                        "Lokasi tidak boleh kosong"
                    )

                    return false
                }

                if (inputDate.text.isNullOrEmpty()) {
                    ValidatorAuthHelper.showToast(
                        this@AddPostActivity,
                        "Tanggal tidak boleh kosong"
                    )

                    return false
                }
                if (inputTime.text.isNullOrEmpty()) {
                    ValidatorAuthHelper.showToast(
                        this@AddPostActivity,
                        "Waktu tidak boleh kosong"
                    )
                    return false
                }
            }
        }
        return true
    }

    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                this@AddPostActivity,
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                this@AddPostActivity,
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                this@AddPostActivity,
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                this@AddPostActivity,
                getString(R.string.error_server_500)
            )
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeAdminActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}