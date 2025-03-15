package com.example.sikoma.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityAuthSessionBinding
import com.example.sikoma.ui.viewmodels.AuthViewModel
import com.example.sikoma.ui.viewmodels.factory.AuthViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthSessionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthSessionBinding

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory.getInstance(this@AuthSessionActivity)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val pref = viewModel.preferences

        lifecycleScope.launch {
            delay(3000)

            val role = pref.getRole().first()

            val nextActivity = when (role) {
                "user" -> HomeActivity::class.java
                "admin" -> HomeAdminActivity::class.java
                else -> AuthActivity::class.java
            }

            startActivity(Intent(this@AuthSessionActivity, nextActivity))

            finish()
        }
    }
}