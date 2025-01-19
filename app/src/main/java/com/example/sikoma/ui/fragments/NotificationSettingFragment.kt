package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.databinding.FragmentNotificationSettingBinding
import com.example.sikoma.ui.adapters.AllPostAdapter
import com.example.sikoma.ui.adapters.NotificationListAdapter
import com.example.sikoma.ui.adapters.NotificationOrganizationAdapter
import com.example.sikoma.ui.adapters.NotificationTopicAdapter
import com.example.sikoma.ui.adapters.OrganizationListAdapter
import com.example.sikoma.ui.adapters.TopicListAdapter
import com.example.sikoma.ui.viewmodels.FollowViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NotificationSettingFragment : Fragment() {

    private lateinit var binding: FragmentNotificationSettingBinding
    private lateinit var adapterOrg : NotificationOrganizationAdapter
    private lateinit var adapterTag : NotificationTopicAdapter
    
    private val viewModel: FollowViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref : UserPreferences
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = viewModel.preferences

        lifecycleScope.launch {
            userId = pref.getUser().first()?.userId.toString()
        }

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
        setView()
        setAdapter()
    }

    private fun setAdapter() {
         viewModel.getFollowAdmin(userId).observe(requireActivity()){
            when {
                it.status == "success" -> {
                    if(it.data != null) {
                        val posts = it.data
                        
                        adapterOrg = NotificationOrganizationAdapter(posts)
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.organizationRv.layoutManager = layoutManager
                        binding.organizationRv.adapter = adapterOrg
                    }
                }
                
                else -> handleError(it.message?.toInt())
            } 
         }
        
        viewModel.getFollowTag(userId).observe(requireActivity()){
            when {
                it.status == "success" -> {
                    if(it.data != null) {
                        val posts = it.data

                        adapterTag = NotificationTopicAdapter(posts)
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.topicRv.layoutManager = layoutManager
                        binding.topicRv.adapter = adapterTag
                    }
                }

                else -> handleError(it.message?.toInt())
            }
        }
    }

    private fun setView() {
        binding.apply {
            buttonBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
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