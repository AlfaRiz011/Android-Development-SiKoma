package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.databinding.ActivityHomeBinding
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.MyEventFragment
import com.example.sikoma.ui.fragments.NotificationFragment
import com.example.sikoma.ui.fragments.SearchFragment
import com.example.sikoma.ui.viewmodels.UserViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: UserViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var pref : UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = viewModel.preferences

        setOnBack()
        setAppBar()
        setNavBar()
        setFragment(HomeFragment())
    }

    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        lifecycleScope.launch{
            val userProfilePic = pref.getUser().firstOrNull()?.profilePic ?: R.drawable.icon_profile_fill

            Glide.with(this@HomeActivity)
                .load(userProfilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(binding.profilePic)
        }

        binding.profilePic.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setNavBar() {
        binding.navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    setFragment(HomeFragment())
                    true
                }

                R.id.nav_search -> {
                    setFragment(SearchFragment())
                    true
                }

                R.id.nav_notification -> {
                    setFragment(NotificationFragment())
                    true
                }

                R.id.nav_my_event -> {
                    setFragment(MyEventFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setOnBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerHome.id, fragment)
            .commit()
    }
}