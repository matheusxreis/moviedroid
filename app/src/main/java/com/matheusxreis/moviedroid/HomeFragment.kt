package com.matheusxreis.moviedroid

import MoviesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.adapters.TopMoviesCarouselAdapter
import com.matheusxreis.moviedroid.models.MoviePoster
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var topMoviesCarouselAdapter: TopMoviesCarouselAdapter
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewPagerCarousel()
        populateViewPagerCarousel()
        populateRecyclerView()

    }


    fun setUpViewPagerCarousel(){
        viewPagerCarousel.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        viewPagerCarousel.adapter = topMoviesAdapter
    }
    fun populateViewPagerCarousel(){
        val mockData = listOf(
            MoviePoster(
                imageUrl = "https://occ-0-2873-987.1.nflxso.net/dnm/api/v6/E8vDc_W8CLv7-yMQu8KMEC7Rrr8/AAAABbFI2wcwiGkHDdGWaw58hWgLETOBsbqqv6GbKnZFn3s_Y4fjw0Ys9DNYD5txnfV3oj9tgsBeaSnPcBOwQqQnpHVqHeQr9FtvVzaL.jpg?r=776",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://static.hbo.com/game-of-thrones-1-1920x1080.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://flxt.tmsimg.com/assets/p186698_b_v9_ay.jpg",
                imdbId = ""
            ))

        topMoviesAdapter.setData(mockData)
    }

    fun setUpRecyclerView() {

        seriesRv.adapter = seriesAdapter
        seriesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        moviesRv.adapter = moviesAdapter
        moviesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    fun populateRecyclerView() {
        val mockData = listOf(
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg",
                imdbId = ""
            ),
        )

        val mockDataSeries = listOf(
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
            MoviePoster(
                imageUrl = "https://br.web.img2.acsta.net/pictures/22/05/04/18/36/1857369.jpg",
                imdbId = ""
            ),
        )

        moviesAdapter.setData(mockData)
        seriesAdapter.setData(mockDataSeries)
    }

}