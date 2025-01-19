package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentSearchOrganizationBinding
import com.example.sikoma.ui.adapters.OrganizationListAdapter
import com.example.sikoma.ui.viewmodels.AdminViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class SearchOrganizationFragment : Fragment() {

    private lateinit var binding: FragmentSearchOrganizationBinding
    private lateinit var organizationListAdapter : OrganizationListAdapter

    private val viewModel: AdminViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchOrganizationBinding.inflate(layoutInflater)
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

        viewModel.getAllAdmins().observe(requireActivity()){
            when {
                it.status == "success" -> {
                    if (it.data != null){
                        val org = it.data

                            binding.noData.visibility = View.GONE
                            organizationListAdapter = OrganizationListAdapter(org)
                            val layoutManager = LinearLayoutManager(requireContext())
                            binding.rvSearchOrganization.layoutManager = layoutManager
                            binding.rvSearchOrganization.adapter = organizationListAdapter
                    } else {
                        binding.noData.visibility = View.VISIBLE
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