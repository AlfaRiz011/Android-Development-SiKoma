package com.example.sikoma.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.User
import com.example.sikoma.databinding.ActivityProfileBinding
import com.example.sikoma.ui.viewmodels.UserViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user : User

    private val viewModel: UserViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var pref : UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = viewModel.preferences

        lifecycleScope.launch {
            user = pref.getUser().firstOrNull()!!
            setData()
            setAction()
        }
    }

    private fun setAction() {
        binding.apply {
            buttonBack.setOnClickListener { finish() }

            buttonLogout.setOnClickListener {
                lifecycleScope.launch {
                    val role = pref.getRole().first()

                    when (role) {
                        "user" -> pref.deleteUser()
                        "admin" -> pref.deleteAdmin()
                    }

                    pref.logOut()

                    val intent = Intent(this@ProfileActivity, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }

            buttonEditData.setOnClickListener { editDataAction() }
        }
    }

    private fun editDataAction() {
        binding.apply {
            buttonEditData.visibility = View.GONE
            buttonDone.visibility = View.VISIBLE
            buttonEditData.isEnabled = false

            val inputs = listOf(inputEmail, inputFullName, inputNim, inputStudyProgram, inputFaculty)

            inputs.forEach {
                it.isFocusableInTouchMode = true
                it.isFocusable = true
            }

            inputEmail.requestFocus()

            buttonDone.setOnClickListener {
                inputs.forEach {
                    it.isFocusableInTouchMode = false
                    it.isFocusable = false
                }

                buttonEditData.visibility = View.VISIBLE
                buttonDone.visibility = View.GONE
                buttonEditData.isEnabled = true
            }
        }
    }

    private fun setData() {
        lifecycleScope.launch {
            binding.apply {
                inputEmail.setText(user.email)
                inputFullName.setText(user.fullName)
                inputNim.setText(user.nim)
                inputStudyProgram.setText(user.studyProg)
                inputFaculty.setText(user.faculty)

                Glide.with(this@ProfileActivity)
                    .load(user.profilePic)
                    .placeholder(R.drawable.icon_profile_fill)
                    .into(profilePic)
            }
        }
    }
}