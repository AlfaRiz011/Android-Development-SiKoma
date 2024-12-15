package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityProfileOrganizationBinding
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.MyEventFragment
import com.example.sikoma.ui.fragments.NotificationFragment
import com.example.sikoma.ui.fragments.SearchFragment

class ProfileOrganizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileOrganizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileOrganizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val color = ContextCompat.getColor(this, R.color.blueSecondary)
            v.setBackgroundColor(color)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

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

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerHome.id, fragment)
            .commit()
    }
}
