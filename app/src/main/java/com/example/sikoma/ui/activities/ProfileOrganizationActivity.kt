package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityAuthBinding
import com.example.sikoma.databinding.ActivityProfileOrganizationBinding
import com.example.sikoma.ui.fragments.AllPostFragment
import com.example.sikoma.ui.fragments.EventPostFragment
import com.example.sikoma.ui.fragments.ForYouFragment
import com.example.sikoma.ui.fragments.HomeFragment
import com.example.sikoma.ui.fragments.LoginFragment
import com.example.sikoma.ui.fragments.MyEventFragment
import com.example.sikoma.ui.fragments.NotificationFragment
import com.example.sikoma.ui.fragments.SearchFragment
import com.google.android.material.tabs.TabLayout

class ProfileOrganizationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileOrganizationBinding

    private lateinit var author: String

    private var profilePic: Int? = null

    private lateinit var bio: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOrganizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        author = intent.getStringExtra("author") ?: "Unknown"
        profilePic = intent.getIntExtra("profilePic", R.drawable.icon_profile_fill)
        bio = intent.getStringExtra("bio") ?: "Unknown"

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
                        2 -> switchFragment(EventPostFragment())
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

    private fun setView(){
        binding.apply {

            organizationName.text = author

            organizationBio.text = bio

            Glide.with(this@ProfileOrganizationActivity)
                .load(profilePic)
                .placeholder(R.drawable.icon_profile_fill)
                .into(binding.profilePic)
        }
    }

}
