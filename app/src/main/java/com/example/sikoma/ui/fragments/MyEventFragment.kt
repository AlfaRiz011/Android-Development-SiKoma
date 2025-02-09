package com.example.sikoma.ui.fragments

import MyEventAdapter
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
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.databinding.FragmentMyEventBinding
import com.example.sikoma.ui.viewmodels.EventViewModel
import com.example.sikoma.ui.viewmodels.factory.EventViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyEventFragment : Fragment() {

    private var userId: String = ""
    private lateinit var binding: FragmentMyEventBinding
    private lateinit var adapter: MyEventAdapter

    private val viewModel: EventViewModel by activityViewModels {
        EventViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var pref : UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyEventBinding.inflate(layoutInflater)
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

        setOnBack()
        setAdapter()
    }

    private fun setAdapter() {
        viewModel.getParticipantUserId(userId).observe(requireActivity()) {
            when {
                it.status == "success" -> {
                    if (it.data != null) {
                        binding.noData.visibility = View.GONE

                        adapter = MyEventAdapter(it.data)
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.rvMyEventPost.layoutManager = layoutManager
                        binding.rvMyEventPost.adapter = adapter
                    } else {
                        binding.noData.visibility = View.VISIBLE
                    }
                }
                else -> handleError(it.message?.toInt())
            }
        }
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
        binding.noData.visibility =
            if (!isLoading) View.VISIBLE else View.GONE
    }
}