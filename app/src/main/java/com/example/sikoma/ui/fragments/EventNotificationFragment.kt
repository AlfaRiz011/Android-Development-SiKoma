package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.FragmentEventNotificationBinding
import com.example.sikoma.ui.adapters.NotificationListAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventNotificationFragment : Fragment() {

    private lateinit var binding : FragmentEventNotificationBinding
    private lateinit var adapter : NotificationListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBack()
        setAdapter()
    }

    private fun setAdapter() {

//        val groupedItems = groupPostsByDate(posts)

//        adapter = NotificationListAdapter(groupedItems)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEventNotification.layoutManager = layoutManager
        binding.rvEventNotification.adapter = adapter
    }
    private fun groupPostsByDate(posts: List<Post>): List<Any> {
        val groupedItems = mutableListOf<Any>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(java.util.Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = dateFormat.format(calendar.time)

//        val grouped = posts.groupBy { it.postDate }

//        for ((dateStr, postsOnDate) in grouped) {
//            val sectionTitle = when {
//                dateStr == today -> "Today"
//                dateStr == tomorrow -> "Tomorrow"
//                else -> dateStr
//            }
//            groupedItems.add(Notification.DateSectionItem(sectionTitle))
//            postsOnDate.forEach { post ->
//                groupedItems.add(Notification.PostItem(post))
//            }
//        }

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