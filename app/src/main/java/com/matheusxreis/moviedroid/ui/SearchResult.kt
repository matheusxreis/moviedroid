package com.matheusxreis.moviedroid.ui

import MoviesAdapter
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels

import androidx.recyclerview.widget.GridLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.databinding.ActivitySearchResultBinding
import com.matheusxreis.moviedroid.utils.MySuggestionProvider
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_result.*

@AndroidEntryPoint
class SearchResult : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivitySearchResultBinding
    private val resultSearchAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private lateinit var searchView: SearchView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeViewModel = homeViewModel



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        handleIntent()
        setUpRecyclerView()
        populateRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_fragment, menu)

        val searchItem = menu?.findItem(R.id.search_menu)

        searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
        searchView.onActionViewCollapsed()


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.delete_search_history) {
            SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                .clearHistory()
        }
        return true
    }


    override fun onPause() {
        searchView.clearFocus()
        searchView.onActionViewCollapsed()

        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /// custom functions

    private fun setUpRecyclerView() {

        searchResultRv.adapter = resultSearchAdapter
        searchResultRv.layoutManager = GridLayoutManager(this, 2)
        showShimmer()
    }

    private fun populateRecyclerView() {
        // homeViewModel.search()
        homeViewModel.searchedResult.observe(this) {

            when (it) {
                is NetworkResult.Error -> {
                    hideShimmer()

                }
                is NetworkResult.Success -> {
                    resultSearchAdapter.setData(it.data!!)
                    hideShimmer()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }


        }
    }

    private fun showShimmer() {
        searchResultRv.showShimmer()
    }

    private fun hideShimmer() {
        searchResultRv.hideShimmer()
    }

    private fun handleIntent() {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                supportActionBar?.setTitle("searching: $query")
                homeViewModel.search(query)
                SearchRecentSuggestions(
                    this,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                    .saveRecentQuery(query, null)
            }
        }

    }


}