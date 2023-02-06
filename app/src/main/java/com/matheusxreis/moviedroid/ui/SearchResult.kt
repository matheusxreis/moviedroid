package com.matheusxreis.moviedroid.ui

import MoviesAdapter
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.bindingadapters.SearchResultBinding
import com.matheusxreis.moviedroid.databinding.ActivitySearchResultBinding
import com.matheusxreis.moviedroid.databinding.FragmentSearchResultBinding
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_result.*

@AndroidEntryPoint
class SearchResult : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivitySearchResultBinding
    private val resultSearchAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeViewModel = homeViewModel

        handleIntent()
        setUpRecyclerView()
        populateRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_fragment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.search_menu){
            onSearchRequested()
        }
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
                homeViewModel.search(query)
            }
        }

    }
}