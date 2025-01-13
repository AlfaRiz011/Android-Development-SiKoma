package com.example.sikoma.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.local.dataStore
import com.example.sikoma.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        val pref = UserPreferences.getInstance(this.dataStore)

        lifecycleScope.launch {
            delay(5000)

            val isSession = pref.getSession().first()
            val role = pref.getRole().first()

            val nextActivity = when {
                !isSession -> AuthActivity::class.java
                role == "user" -> HomeActivity::class.java
                else -> HomeAdminActivity::class.java
            }

            startActivity(Intent(this@SplashActivity, nextActivity))

            finish()
        }
    }
}