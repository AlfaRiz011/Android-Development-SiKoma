package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.FragmentHomeAdminBinding
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class HomeAdminFragment(private val adminId: String) : Fragment() {
    private lateinit var binding: FragmentHomeAdminBinding
    private var isBottomNavVisible = true
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var constraintLayout: ConstraintLayout
    private val viewModel: PostViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintLayout = requireActivity().findViewById(R.id.main)

        setOnBack()
        setImageSlider()
        setTabLayout()
        setView()
    }

    private fun setView() {
        bottomNav = requireActivity().findViewById(R.id.nav_view)

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
                        0 -> switchFragment(AllPostFragment())
                        1 -> switchFragment(EventPostFragment())
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            selectTab(getTabAt(0))
            switchFragment(AllPostFragment())
        }
    }

    private fun setImageSlider() {
        val imageList = ArrayList<SlideModel>()
        var sliderPosts: List<Post>? = null

        viewModel.getAllPost().observe(viewLifecycleOwner) { response ->
            when {
                response.status == "success" -> {
                    val posts = response.data

                    sliderPosts = posts?.filter { it.adminId == 6 }

                    if (sliderPosts.isNullOrEmpty()) {
                        binding.imageSlider.layoutParams.height = 0
                    } else {
                        sliderPosts?.let {
                            for (post in it) {
                                imageList.add(
                                    SlideModel(
                                        post.image,
                                        "\uD83D\uDD34 ${post.description}"
                                    )
                                )
                            }
                        }

                        val imageSlider = binding.imageSlider
                        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

                        binding.imageSlider.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }

                else -> handleError(response.message?.toInt())
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_tab_admin, fragment)
        fragmentTransaction.commit()
    }

    private fun hideBottomNavigationView() {
        bottomNav.animate()
            .translationY(bottomNav.height.toFloat())
            .setDuration(200)
            .start()

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.fragment_container_home,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(constraintLayout)

        isBottomNavVisible = false
    }

    private fun showBottomNavigationView() {
        bottomNav.animate()
            .translationY(0f)
            .setDuration(200)
            .start()

        bottomNav.postDelayed({
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(
                R.id.fragment_container_home,
                ConstraintSet.BOTTOM,
                R.id.nav_view,
                ConstraintSet.TOP
            )
            constraintSet.applyTo(constraintLayout)
        }, 200)

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
}