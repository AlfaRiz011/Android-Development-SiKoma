package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.data.models.OrganizationProvider
import com.example.sikoma.data.models.PostProvider
import com.example.sikoma.databinding.FragmentSearchBinding
import com.example.sikoma.databinding.FragmentSearchOrganizationBinding
import com.example.sikoma.ui.adapters.AllPostAdapter
import com.example.sikoma.ui.adapters.OrganizationListAdapter

class SearchOrganizationFragment : Fragment() {

    private lateinit var binding: FragmentSearchOrganizationBinding
    private lateinit var organizationListAdapter : OrganizationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchOrganizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        val org = OrganizationProvider.createDummy()

        organizationListAdapter = OrganizationListAdapter(org)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchOrganization.layoutManager = layoutManager
        binding.rvSearchOrganization.adapter = organizationListAdapter
    }
}