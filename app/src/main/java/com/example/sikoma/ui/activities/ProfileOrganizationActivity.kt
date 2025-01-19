package com.example.sikoma.ui.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityProfileOrganizationBinding
import com.example.sikoma.ui.fragments.AllPostFragment
import com.example.sikoma.ui.fragments.EventPostFragment
import com.example.sikoma.ui.viewmodels.AdminViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.material.tabs.TabLayout

class ProfileOrganizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileOrganizationBinding
    private lateinit var adminId: String

    private val viewModel: AdminViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOrganizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        adminId = intent.getStringExtra("adminId") ?: ""

        setView()
        setTabLayout()
    }

    private fun setTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText("Post"))
            addTab(newTab().setText("Event"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> switchFragment(AllPostFragment())
                        1 -> switchFragment(EventPostFragment())
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            selectTab(getTabAt(0))
            switchFragment(AllPostFragment())
        }

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerTab.id, fragment)
            .commit()
    }

    private fun setView() {
        viewModel.getAdminByID(adminId).observe(this) {
            when {
                it.status == "success" -> {
                    if (it.data != null) {

                        binding.organizationName.text = it.data.organizationName
                        binding.organizationBio.text = it.data.bio

                        Glide.with(this@ProfileOrganizationActivity)
                            .load(it.data.organizationName?.let{ img -> Uri.parse(img) })
                            .placeholder(R.drawable.icon_profile_fill)
                            .into(binding.profilePic)
                    }
                }
                else -> handleError(it.message?.toInt())
            }
        }
    }

    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                this,
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                this,
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                this,
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                this,
                getString(R.string.error_server_500)
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
