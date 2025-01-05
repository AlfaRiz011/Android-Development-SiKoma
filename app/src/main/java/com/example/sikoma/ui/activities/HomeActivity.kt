package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityHomeBinding
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.MyEventFragment
import com.example.sikoma.ui.fragments.NotificationFragment
import com.example.sikoma.ui.fragments.SearchFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBack()
        setAppBar()
        setNavBar()
        setFragment(HomeFragment())
    }

    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        Glide.with(this)
            .load(R.drawable.logo_upn)
            .placeholder(R.drawable.icon_profile_fill)
            .into(binding.profilePic)


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