package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentEventPostBinding
import com.example.sikoma.ui.adapters.EventPostAdapter
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class EventPostFragment : Fragment() {
    private lateinit var binding: FragmentEventPostBinding
    private lateinit var postAdapter: EventPostAdapter

    private val viewModel: PostViewModel by activityViewModels {
        PostViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventPostBinding.inflate(layoutInflater)
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
        viewModel.getEventPost().observe(requireActivity()){
            when {
                it.status == "success" ->{
                    val posts = it.data

                    if(posts.isNullOrEmpty()){
                        binding.noData.visibility = View.VISIBLE
                    } else {
                        binding.noData.visibility = View.GONE
                        postAdapter = EventPostAdapter(posts)
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.rvEventPost.layoutManager = layoutManager
                        binding.rvEventPost.adapter = postAdapter
                    }
                }

                else -> handleError(it.message?.toInt())
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