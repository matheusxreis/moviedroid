package com.matheusxreis.moviedroid.ui.fragments.home

import MoviesAdapter
import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.snackbar.Snackbar
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.TopMoviesCarouselAdapter
import com.matheusxreis.moviedroid.bindingadapters.HomeBinding
import com.matheusxreis.moviedroid.utils.MySuggestionProvider
import com.matheusxreis.moviedroid.utils.NetworkListener
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.matheusxreis.moviedroid.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private val seriesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private val topMoviesAdapter: TopMoviesCarouselAdapter by lazy {
        TopMoviesCarouselAdapter() {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie = it)
            findNavController().navigate(action)
        }
    }
    private lateinit var searchView: SearchView;


    private var hasDesconnected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)
        binding.homeViewModel = homeViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        monitoringNetwork()
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
        searchMenu.icon?.setTint(ContextCompat.getColor(requireActivity(), R.color.white))
        searchView = searchMenu.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(requireActivity().textl)
        searchView.clearFocus()

        val searchManager = requireContext().getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(true) // Do not iconify the widget; expand it by default
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

            if (menuItem.itemId == R.id.delete_search_history) {
                SearchRecentSuggestions(requireActivity(), MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                    .clearHistory()
                Snackbar.make(binding.root, "Search History Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Okay", {})
                    .show()
            }

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
                when(it) {
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Success -> {
                        topMoviesAdapter.setData(it.data!!.subList(0, 3))
                    }
                    is NetworkResult.Loading -> {

                    }
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

                when(it){
                    is NetworkResult.Error -> {
                        hideShimmer("movie")
                    }
                    is NetworkResult.Success -> {
                        moviesAdapter.setData(it.data!!)
                        hideShimmer("movie")

                    }
                    is NetworkResult.Loading -> {
                        showShimmer("movie")
                    }
                }

        }

        homeViewModel.popularSeries.observe(viewLifecycleOwner) {

                when(it){
                    is NetworkResult.Error -> {
                        hideShimmer("tv")
                    }
                    is NetworkResult.Success -> {
                        seriesAdapter.setData(it.data!!)
                        hideShimmer("tv")
                    }
                    is NetworkResult.Loading -> {
                        showShimmer("tv")
                    }
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


    private fun monitoringNetwork() =  lifecycleScope.launch {

        val connection = homeViewModel.networkListener.checkNetworkavailability(requireContext())
        connection.collect {
            if (!it) {
                Toast.makeText(
                    context,
                    "No network",
                    Toast.LENGTH_SHORT
                )
                    .show()

                hasDesconnected = true
            } else {
                if (hasDesconnected) {
                    populateViewPagerCarousel()
                    populateRecyclerView()
                    Toast.makeText(context,
                        "Connected",
                        Toast.LENGTH_SHORT)
                        .show()
                }else {
                    hasDesconnected = false
                }
            }
        }

    }


}