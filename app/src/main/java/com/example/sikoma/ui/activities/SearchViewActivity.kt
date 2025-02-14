package com.example.sikoma.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sikoma.R
import com.example.sikoma.data.models.Post
import com.example.sikoma.databinding.ActivitySearchViewBinding
import com.example.sikoma.ui.adapters.AllPostAdapter
import com.example.sikoma.ui.viewmodels.PostViewModel
import com.example.sikoma.ui.viewmodels.TagViewModel
import com.example.sikoma.ui.viewmodels.factory.PostViewModelFactory
import com.example.sikoma.ui.viewmodels.factory.TagViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class SearchViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchViewBinding

    private lateinit var postAdapter: AllPostAdapter

    private lateinit var tagName: String

    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView

    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tagName = intent.getStringExtra("tagName") ?: ""

        setView()
        setSearch()
        setLoading()
        setAppBar()
        setAdapter()
    }


    private fun setAppBar() {
        setSupportActionBar(binding.toolbar)
        binding.buttonBack.setOnClickListener { finish() }

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }


    private fun setAdapter() {
        viewModel.getPostByTag(tagName).observe(this) { respond ->
            when (respond.status) {
                "success" -> {
                    respond.data?.let { posts ->
                        updateAdapter(posts)
                    } ?: run {
                        updateAdapter(emptyList())
                    }
                }
                "error" -> {
                    updateAdapter(emptyList())
                }
                else -> handleError(respond.message?.toInt())
            }
        }
    }

    private fun setView() {
        searchBar = binding.searchBar
        searchView = binding.searchView
        searchBar.setText(tagName)
    }

    private fun setSearch() {
        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = searchView.editText.text.toString().trim()
            searchBar.setText(query)
            if (query.isNotEmpty()) {
                searchData(query)
            } else {
                updateAdapter(emptyList())
            }
            searchView.hide()
            true
        }
    }

    private fun searchData(query: String) {
        viewModel.getPostByTag(query).observe(this) { respond ->
            when (respond.status) {
                "success" -> {
                    val posts = respond.data ?: emptyList()
                    updateAdapter(posts)
                }
                else -> {
                    updateAdapter(emptyList())
                }
            }
        }
    }

    private fun updateAdapter(posts: List<Post>) {
        if (posts.isEmpty()) {
            binding.rvAllPost.adapter = null
            binding.noData.visibility = View.VISIBLE
        } else {
            binding.noData.visibility = View.GONE
            postAdapter = AllPostAdapter(posts)
            val layoutManager = LinearLayoutManager(this)
            binding.rvAllPost.layoutManager = layoutManager
            binding.rvAllPost.adapter = postAdapter
        }
    }

    private fun setLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                this@SearchViewActivity,
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                this@SearchViewActivity,
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                this@SearchViewActivity,
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                this@SearchViewActivity,
                getString(R.string.error_server_500)
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}