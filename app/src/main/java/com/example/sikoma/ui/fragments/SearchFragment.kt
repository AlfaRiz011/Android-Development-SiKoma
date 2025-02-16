package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.sikoma.R
import com.example.sikoma.data.models.Tag
import com.example.sikoma.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBack()
        setTabLayout()
        setSearch()
    }

    private fun setSearch() {
        binding.searchView.setupWithSearchBar(binding.searchbar)
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchView.editText.text.toString().trim()
            binding.searchbar.setText(query)
            searchData(query)
            binding.searchView.hide()
            true
        }
    }

    private fun searchData(query: String) {

        when (binding.tabLayout.selectedTabPosition) {
            0 -> {
                switchFragment(SearchOrganizationFragment.newInstance(query))
            }

            1 -> {
                switchFragment(SearchTopicFragment.newInstance(query))
            }

            else -> {
                switchFragment(SearchOrganizationFragment.newInstance(query))
            }
        }
    }

    private fun setTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText("Organization"))
            addTab(newTab().setText("Topic"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> switchFragment(SearchOrganizationFragment.newInstance(binding.searchbar.text.toString()))
                        1 -> switchFragment(SearchTopicFragment.newInstance(binding.searchbar.text.toString()))
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            selectTab(getTabAt(0))
            switchFragment(SearchOrganizationFragment.newInstance(null))
        }

    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_search, fragment)
        fragmentTransaction.commit()
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