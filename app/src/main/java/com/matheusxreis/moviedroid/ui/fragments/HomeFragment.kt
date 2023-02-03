package com.matheusxreis.moviedroid.ui.fragments

import MoviesAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.TopMoviesCarouselAdapter
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private val seriesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private val topMoviesAdapter: TopMoviesCarouselAdapter by lazy {
        TopMoviesCarouselAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewPagerCarousel()
        populateViewPagerCarousel()
        populateRecyclerView()

        viewPagerCarousel.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectSliderCarousel(position)

            }
        })

    }


    fun setUpViewPagerCarousel() {
        viewPagerCarousel.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        viewPagerCarousel.adapter = topMoviesAdapter
    }

    fun populateViewPagerCarousel() {

        homeViewModel.getTrendingSeries()
        homeViewModel.trendingSeries.observe(viewLifecycleOwner) {

            if (it != null) {
                topMoviesAdapter.setData(it.subList(0, 3))
            }
        }
    }

    fun setUpRecyclerView() {

        seriesRv.adapter = seriesAdapter
        seriesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        moviesRv.adapter = moviesAdapter
        moviesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        showShimmer()
    }

    fun populateRecyclerView() {


        homeViewModel.getMovies("popular")
        homeViewModel.getTv("popular")

        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                moviesAdapter.setData(it)
            }
        }

        homeViewModel.popularSeries.observe(viewLifecycleOwner) {
            if (it != null) {
                seriesAdapter.setData(it)
            }
        }


        hideShimmer()
    }


    fun selectSliderCarousel(number: Number) {
        when (number) {
            0 -> {
                markTopMovies1.alpha = 1f
                markTopMovies2.alpha = 0.5f
                markTopMovies3.alpha = 0.5f

            }
            1 -> {
                markTopMovies1.alpha = 0.5f
                markTopMovies2.alpha = 1f
                markTopMovies3.alpha = 0.5f
            }
            else -> {
                markTopMovies1.alpha = 0.5f
                markTopMovies2.alpha = 0.5f
                markTopMovies3.alpha = 1f
            }
        }

    }


    fun showShimmer() {
        moviesRv.showShimmer()
        seriesRv.showShimmer()
    }

    fun hideShimmer() {
        moviesRv.hideShimmer()
        seriesRv.hideShimmer()
    }

}