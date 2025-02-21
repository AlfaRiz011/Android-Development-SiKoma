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
import com.example.sikoma.data.local.dataStore
import com.example.sikoma.databinding.ActivityHomeAdminBinding
import com.example.sikoma.ui.fragments.HomeAdminFragment
import com.example.sikoma.ui.fragments.StatisticFragment
import com.example.sikoma.ui.viewmodels.AdminViewModel
import com.example.sikoma.ui.viewmodels.factory.AdminViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeAdminBinding

    private lateinit var pref: UserPreferences

    private lateinit var adminId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = UserPreferences.getInstance(this.dataStore)

        lifecycleScope.launch {
            val admin = pref.getAdmin().first()
            adminId = admin?.adminId.toString()

            setAppBar()
            setOnBack()

            withContext(Dispatchers.Main) {
                setNavBar()
                setFragment(HomeAdminFragment.newInstance(adminId))
            }
        }
    }

    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setNavBar() {
        binding.navViewAdmin.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home_admin -> {
                    setFragment(HomeAdminFragment.newInstance(adminId))
                    true
                }

                R.id.nav_statistic -> {
                    setFragment(StatisticFragment.newInstance(adminId))
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
            .replace(binding.fragmentContainerHomeAdmin.id, fragment)
            .commit()
    }
}