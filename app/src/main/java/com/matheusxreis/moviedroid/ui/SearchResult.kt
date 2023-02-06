package com.matheusxreis.moviedroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.bindingadapters.SearchResultBinding
import com.matheusxreis.moviedroid.databinding.ActivitySearchResultBinding
import com.matheusxreis.moviedroid.databinding.FragmentSearchResultBinding
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResult : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeViewModel = homeViewModel


    }
}