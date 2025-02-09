package com.example.sikoma.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentHomeBinding
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isBottomNavVisible = true
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var constraintLayout: ConstraintLayout

    private val viewModel: PostViewModel by activityViewModels {
        PostViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

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
            addTab(newTab().setText("For You"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> switchFragment(AllPostFragment())
                        1 -> switchFragment(EventPostFragment())
                        2 -> switchFragment(ForYouFragment())
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

        viewModel.getAllPost().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                "success" -> {
                    response.data?.let { posts ->
                        for (post in posts) {
                            Log.d("Image URL", "URL: ${post.image}")
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
                }

                else -> handleError(response.message?.toInt())
            }
        }

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val title = imageList[position].title
                Toast.makeText(requireContext(), "Clicked $title", Toast.LENGTH_SHORT).show()
            }

            override fun doubleClick(position: Int) {}
        })
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_tab, fragment)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
