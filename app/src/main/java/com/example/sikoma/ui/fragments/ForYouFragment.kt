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
import com.example.sikoma.databinding.FragmentForYouBinding
import com.example.sikoma.ui.adapters.ForYouAdapter
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ForYouFragment : Fragment() {
    private lateinit var binding: FragmentForYouBinding
    private lateinit var postAdapter: ForYouAdapter

    private val viewModel: PostViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private val pref = viewModel.preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(layoutInflater)
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

        lifecycleScope.launch {
            val userId = pref.getUser().firstOrNull()?.userId.toString()

            viewModel.getRecommendationPost(userId).observe(requireActivity()) { response ->
                when {
                    response.status == "success" -> {
                        val posts = response.data

                        if (posts.isNullOrEmpty()) {
                            binding.noData.visibility = View.VISIBLE
                        } else {
                            postAdapter = ForYouAdapter(posts)
                            val layoutManager = LinearLayoutManager(requireContext())
                            binding.rvForYou.layoutManager = layoutManager
                            binding.rvForYou.adapter = postAdapter

                        }
                    }

                    else -> handleError(response.message?.toInt())
                }
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