package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentSearchTopicBinding
import com.example.sikoma.ui.adapters.TopicListAdapter
import com.example.sikoma.ui.viewmodels.TagViewModel
import com.example.sikoma.ui.viewmodels.factory.TagViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class SearchTopicFragment : Fragment() {

    private lateinit var binding: FragmentSearchTopicBinding
    private lateinit var topicListAdapter: TopicListAdapter

    private val viewModel: TagViewModel by activityViewModels {
        TagViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTopicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        setAdapter()
    }

    private fun setAdapter() {
        viewModel.getAllTag().observe(viewLifecycleOwner) { result ->
            if (result.status == "success") {
                val topics = result.data
                if (topics != null) {
                    binding.noData.visibility = View.GONE
                    topicListAdapter = TopicListAdapter(topics)
                    binding.rvSearchTopic.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = topicListAdapter
                    }
                } else {
                    binding.noData.visibility = View.VISIBLE
                }
            } else {
                handleError(result.message?.toInt())
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
        binding.noData.visibility =
            if (!isLoading) View.VISIBLE else View.GONE
    }
}