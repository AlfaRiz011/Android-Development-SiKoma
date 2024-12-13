package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentAllPostBinding
import com.example.sikoma.databinding.FragmentForYouBinding

class ForYouFragment : Fragment() {
    private lateinit var binding: FragmentForYouBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(layoutInflater)
        return binding.root
    }

}