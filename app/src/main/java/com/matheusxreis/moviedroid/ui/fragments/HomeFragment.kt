package com.matheusxreis.moviedroid.ui.fragments

import MoviesAdapter
import android.os.Bundle
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

    private lateinit var homeViewModel: ViewModel
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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

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
        val mockData = listOf(
            MoviePoster(
                imageUrl = "https://occ-0-2873-987.1.nflxso.net/dnm/api/v6/E8vDc_W8CLv7-yMQu8KMEC7Rrr8/AAAABbFI2wcwiGkHDdGWaw58hWgLETOBsbqqv6GbKnZFn3s_Y4fjw0Ys9DNYD5txnfV3oj9tgsBeaSnPcBOwQqQnpHVqHeQr9FtvVzaL.jpg?r=776",
                imdbId = "",
                title = "Breaking Bad"
            ),
            MoviePoster(
                imageUrl = "https://static.hbo.com/game-of-thrones-1-1920x1080.jpg",
                imdbId = "",
                title = "Game of Thrones"
            ),
            MoviePoster(
                imageUrl = "https://flxt.tmsimg.com/assets/p186698_b_v9_ay.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            )
        )
        topMoviesAdapter.setData(mockData)
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
        val mockData = listOf(
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"

            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
        )

        val mockDataSeries = listOf(
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = "",
                title = "Sons of Anarchy"
            ),
        )

        moviesAdapter.setData(mockData)
        seriesAdapter.setData(mockDataSeries)

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