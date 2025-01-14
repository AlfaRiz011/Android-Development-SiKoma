package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.data.models.DateSection
import com.example.sikoma.data.models.Notification
import com.example.sikoma.data.models.Post
import com.example.sikoma.data.models.PostItem
import com.example.sikoma.databinding.FragmentEventNotificationBinding
import com.example.sikoma.ui.adapters.NotificationListAdapter
import com.example.sikoma.ui.viewmodels.NotificationViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventNotificationFragment : Fragment() {

    private lateinit var binding : FragmentEventNotificationBinding
    private lateinit var adapter : NotificationListAdapter

    private val viewModel: NotificationViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private val pref = viewModel.preferences
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            userId = pref.getUser().first()?.userId.toString()
        }

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        setOnBack()
        setAdapter()
    }

    private fun setAdapter() {

        viewModel.getNotifEvent(userId).observe(requireActivity()) {
            when {
                it.status == "success" -> {
                    if (it.data != null) {
                        val posts = it.data

                        val groupedItems = groupPostsByDate(posts)
                        adapter = NotificationListAdapter(groupedItems)
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.rvEventNotification.layoutManager = layoutManager
                        binding.rvEventNotification.adapter = adapter
                    }
                }

                else -> handleError(it.message?.toInt())
            }
        }


    }

    private fun groupPostsByDate(posts: List<Post>): List<Any> {
        val groupedItems = mutableListOf<Any>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(java.util.Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = dateFormat.format(calendar.time)

        val grouped = posts.groupBy { it.eventDate }

        for ((dateStr, postsOnDate) in grouped) {
            val sectionTitle = when (dateStr) {
                today -> "Today"
                tomorrow -> "Tomorrow"
                else -> dateStr
            }
            groupedItems.add(DateSection(sectionTitle))
            postsOnDate.forEach { post ->
                groupedItems.add(PostItem(post))
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

    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_server_500)
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}