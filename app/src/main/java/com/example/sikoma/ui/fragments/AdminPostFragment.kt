package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentAdminPostBinding
import com.example.sikoma.ui.adapters.AllPostAdapter
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class AdminPostFragment : Fragment() {

    private lateinit var binding: FragmentAdminPostBinding
    private lateinit var postAdapter: AllPostAdapter
    private val adminId: String?
        get() = arguments?.getString(ARG_ADMIN_ID)

    private val viewModel: PostViewModel by activityViewModels {
        PostViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminPostBinding.inflate(layoutInflater)
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
        adminId?.let { id ->
            viewModel.getPostAdmin(id).observe(requireActivity()) { response ->
                when (response.status) {
                    "success" -> {
                        val posts = response.data

                        if (posts.isNullOrEmpty()) {
                            binding.noData.visibility = View.VISIBLE
                        } else {
                            binding.noData.visibility = View.GONE
                            postAdapter = AllPostAdapter(posts)
                            val layoutManager = LinearLayoutManager(requireContext())
                            binding.rvAllPost.layoutManager = layoutManager
                            binding.rvAllPost.adapter = postAdapter
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

    companion object {
        private const val ARG_ADMIN_ID = "arg_admin_id"
        fun newInstance(adminId: String?): AdminPostFragment {
            return AdminPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ADMIN_ID, adminId)
                }
            }
        }
    }
}