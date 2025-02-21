package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentStatisticBinding
import com.example.sikoma.utils.BottomNavView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class StatisticFragment : Fragment() {
    private lateinit var binding: FragmentStatisticBinding

    private var isBottomNavVisible = true
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var constraintLayout: ConstraintLayout
    private val fragmentContainerId = R.id.fragment_container_home_admin
    private val navViewId = R.id.nav_view_admin

    private val adminId: String?
        get() = arguments?.getString(ARG_ADMIN_ID)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintLayout = requireActivity().findViewById(R.id.main)

        setOnBack()
        setTabLayout()
        setView()
    }

    private fun setView() {
        bottomNav = requireActivity().findViewById(R.id.nav_view_admin)

        binding.stickyScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && isBottomNavVisible) {
                hideBottomNavigationView()
            } else if (scrollY < oldScrollY && !isBottomNavVisible) {
                showBottomNavigationView()
            }
        }
    }

    private fun setTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText("All Posts"))
            addTab(newTab().setText("Event Posts"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> switchFragment(AdminPostFragment.newInstance(adminId))
                        1 -> switchFragment(AdminEventPostFragment.newInstance(adminId))
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            selectTab(getTabAt(0))
            switchFragment(AllPostFragment())
        }
    }


    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_tab_admin, fragment)
        fragmentTransaction.commit()
    }

    private fun hideBottomNavigationView() {
        BottomNavView.hide(bottomNav, constraintLayout, fragmentContainerId)
        isBottomNavVisible = false
    }

    private fun showBottomNavigationView() {
        BottomNavView.show(bottomNav, constraintLayout, fragmentContainerId, navViewId)
        isBottomNavVisible = true
    }

    private fun setOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    companion object {
        private const val ARG_ADMIN_ID = "arg_adminId"
        fun newInstance(query: String?): StatisticFragment {
            return StatisticFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ADMIN_ID, query)
                }
            }
        }
    }
}