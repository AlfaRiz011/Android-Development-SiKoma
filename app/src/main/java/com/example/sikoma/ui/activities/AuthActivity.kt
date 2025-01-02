package com.example.sikoma.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import com.example.sikoma.R
import com.example.sikoma.databinding.ActivityAuthBinding
import com.example.sikoma.ui.fragments.LoginFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val color = ContextCompat.getColor(this, R.color.blueSecondary)
            v.setBackgroundColor(color)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerAuth.id, LoginFragment())
            .commit()
    }

}

