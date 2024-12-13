package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.PostProvider
import com.example.sikoma.databinding.FragmentAllPostBinding
import com.example.sikoma.ui.adapters.AllPostAdapter

class AllPostFragment : Fragment() {

    private lateinit var binding: FragmentAllPostBinding
    private lateinit var postAdapter: AllPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllPostBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posts = PostProvider.createDummy(20)

        

        postAdapter = AllPostAdapter(posts)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllPost.layoutManager = layoutManager
        binding.rvAllPost.adapter = postAdapter
    }
}