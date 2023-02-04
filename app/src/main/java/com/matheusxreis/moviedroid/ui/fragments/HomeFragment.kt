package com.matheusxreis.moviedroid.ui.fragments

import MoviesAdapter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.TopMoviesCarouselAdapter
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this)

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

    // MENU PROVIDER
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_home_fragment, menu)

        val searchMenu = menu.findItem(R.id.search_menu)
        val searchView = searchMenu.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    // SEARCH VIEW
    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


    // CUSTOM FUNCTIONS
    private fun setUpViewPagerCarousel() {
        viewPagerCarousel.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        viewPagerCarousel.adapter = topMoviesAdapter
    }

    private fun populateViewPagerCarousel() {

        homeViewModel.getTrendingSeries()
        homeViewModel.trendingSeries.observe(viewLifecycleOwner) {

            if (it != null) {
                topMoviesAdapter.setData(it.subList(0, 3))
            }
        }
    }

    private fun setUpRecyclerView() {

        moviesAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
        seriesRv.adapter = seriesAdapter
        seriesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        moviesRv.adapter = moviesAdapter
        moviesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        showShimmer()

        moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollHorizontally(1) && !moviesRv.isShimmerShowing) {
                    // moviesProgressBar.visibility = View.VISIBLE
                    // val lastPosition = homeViewModel.popularMovies.value?.size ?: 0
                    // recyclerView.scrollToPosition(lastPosition - 1)
                    // homeViewModel.getNewPageMovies("popular")

                } else {
                    moviesProgressBar.visibility = View.GONE
                }
            }
        })
        seriesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollHorizontally(1) && !seriesRv.isShimmerShowing) {
                }
            }
        })
    }

    private fun populateRecyclerView() {
        homeViewModel.getMovies("popular")
        homeViewModel.getTv("popular")
        homeViewModel.popularMovies.observe(viewLifecycleOwner) {
            if (it != null) {
                moviesAdapter.setData(it)
                hideShimmer("movie")
            }
        }

        homeViewModel.popularSeries.observe(viewLifecycleOwner) {
            if (it != null) {
                seriesAdapter.setData(it)
                hideShimmer("tv")

            }
        }


    }

    private fun selectSliderCarousel(number: Number) {
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

    private fun showShimmer(who: String = "all") {
        when (who) {
            "movies" -> {
                moviesRv.showShimmer()
            }
            "tv" -> {
                seriesRv.showShimmer()
            }
            else -> {
                moviesRv.showShimmer()
                seriesRv.showShimmer()
            }
        }

    }

    private fun hideShimmer(who: String = "all") {

        when (who) {
            "movie" -> {
                moviesRv.hideShimmer()

            }
            "tv" -> {
                seriesRv.hideShimmer()

            }
            else -> {
                moviesRv.hideShimmer()
                seriesRv.hideShimmer()
            }
        }

    }



}