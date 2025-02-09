package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.local.dataStore
import com.example.sikoma.databinding.ActivityHomeBinding
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.MyEventFragment
import com.example.sikoma.ui.fragments.SearchFragment
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pref: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserPreferences.getInstance(this.dataStore)

        setOnBackPressedHandler()
        setupAppBar()
        setupNavBar()
        setFragment(HomeFragment())
    }

    private fun setupAppBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        lifecycleScope.launch {
            val userProfilePic = pref.getUser().firstOrNull()?.profilePic ?: R.drawable.icon_profile_fill
            Glide.with(this@HomeActivity)
                .load(userProfilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(binding.profilePic)
        }

        binding.profilePic.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupNavBar() {
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> setFragment(HomeFragment())
                R.id.nav_search -> setFragment(SearchFragment())
                R.id.nav_my_event -> setFragment(MyEventFragment())
            }
            true
        }
    }

    private fun setOnBackPressedHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerHome.id)
        if (currentFragment?.javaClass != fragment.javaClass) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerHome.id, fragment)
                .commit()
        }
    }
}
