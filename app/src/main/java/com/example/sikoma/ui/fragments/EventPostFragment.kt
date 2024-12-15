package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.data.models.PostProvider
import com.example.sikoma.databinding.FragmentEventPostBinding
import com.example.sikoma.ui.adapters.EventPostAdapter

class EventPostFragment : Fragment() {
    private lateinit var binding: FragmentEventPostBinding
    private lateinit var postAdapter: EventPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventPostBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posts = PostProvider.createDummy(4)

        postAdapter = EventPostAdapter(posts)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEventPost.layoutManager = layoutManager
        binding.rvEventPost.adapter = postAdapter
    }
}