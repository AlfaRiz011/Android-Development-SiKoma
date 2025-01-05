package com.example.sikoma.ui.fragments

import MyEventAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.PostProvider
import com.example.sikoma.databinding.FragmentMyEventBinding  
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyEventFragment : Fragment() {

    private lateinit var binding : FragmentMyEventBinding
    private lateinit var adapter : MyEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBack()
        setAdapter()
    }

    private fun setAdapter() {
        val posts = PostProvider.createDummy(5)

        val groupedItems = groupPostsByDate(posts)

        adapter = MyEventAdapter(groupedItems)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyEventPost.layoutManager = layoutManager
        binding.rvMyEventPost.adapter = adapter
    }
    private fun groupPostsByDate(posts: List<Post>): List<Any> {
        val groupedItems = mutableListOf<Any>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(java.util.Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = dateFormat.format(calendar.time)

        val grouped = posts.groupBy { it.postDate }

        for ((dateStr, postsOnDate) in grouped) {
            val sectionTitle = when {
                dateStr == today -> "Today"
                dateStr == tomorrow -> "Tomorrow"
                else -> dateStr
            }
            groupedItems.add(Notification.DateSectionItem(sectionTitle))
            postsOnDate.forEach { post ->
                groupedItems.add(Notification.PostItem(post))
            }
        }

        return groupedItems
    }

    private fun setOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}