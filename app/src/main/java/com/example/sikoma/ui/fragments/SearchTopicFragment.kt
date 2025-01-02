package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.data.models.TopicProvider
import com.example.sikoma.databinding.FragmentSearchTopicBinding
import com.example.sikoma.ui.adapters.TopicListAdapter

class SearchTopicFragment : Fragment() {

    private lateinit var binding: FragmentSearchTopicBinding
    private lateinit var topicListAdapter: TopicListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTopicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        val topics = TopicProvider.createDummy()

        topicListAdapter = TopicListAdapter(topics)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchTopic.layoutManager = layoutManager
        binding.rvSearchTopic.adapter = topicListAdapter
    }

}