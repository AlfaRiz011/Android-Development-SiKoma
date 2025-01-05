package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityHomeAdminBinding
import com.example.sikoma.ui.fragments.HomeAdminFragment
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.StatisticFragment


class HomeAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnBack()
        setAppBar()
        setNavBar()
        setFragment(HomeAdminFragment())
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
                R.id.nav_home_admin -> {
                    setFragment(HomeAdminFragment())
                    true
                }

                R.id.nav_statistic -> {
                    setFragment(StatisticFragment())
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