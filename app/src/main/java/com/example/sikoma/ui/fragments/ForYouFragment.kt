package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.data.models.PostProvider
import com.example.sikoma.databinding.FragmentForYouBinding
import com.example.sikoma.ui.adapters.ForYouAdapter

class ForYouFragment : Fragment() {
    private lateinit var binding: FragmentForYouBinding
    private lateinit var postAdapter: ForYouAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posts = PostProvider.createDummy(3)

        postAdapter = ForYouAdapter(posts)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvForYou.layoutManager = layoutManager
        binding.rvForYou.adapter = postAdapter
    }
}