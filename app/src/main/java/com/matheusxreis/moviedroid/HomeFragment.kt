package com.matheusxreis.moviedroid

import MoviesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.matheusxreis.moviedroid.adapters.TopMoviesCarouselAdapter
import com.matheusxreis.moviedroid.models.MoviePoster
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var topMoviesCarouselAdapter: TopMoviesCarouselAdapter
    private val moviesAdapter:MoviesAdapter by lazy {
        MoviesAdapter()
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

        val images = listOf(
            "https://pop.proddigital.com.br/wp-content/uploads/sites/8/2021/11/breaking-bad.jpg",
            "https://sm.ign.com/ign_br/gallery/t/the-last-o/the-last-of-us-hbo-series-character-guide_nr24.jpg",
            "https://veja.abril.com.br/wp-content/uploads/2019/04/seriado-game-of-thrones-s8-23-1.jpg-1.jpg?quality=70&strip=info&w=1280&h=720&crop=1"
        )
        val titles = listOf(
            "Breaking Bad",
            "The Last Of Us",
            "Game of Thrones"
        )

        topMoviesCarouselAdapter = TopMoviesCarouselAdapter { index ->
            topMoviesTitleTv.text = titles[index]
            topMoviesTitleTv.setOnClickListener { }
            seeMoreTv.setOnClickListener { }
            if (index == 0) {
                markTopMovies1.alpha = 1f
                markTopMovies2.alpha = 0.5f
                markTopMovies3.alpha = 0.5f
            } else if (index == 1) {
                markTopMovies2.alpha = 1f
                markTopMovies1.alpha = 0.5f
                markTopMovies3.alpha = 0.5f
            } else {
                markTopMovies3.alpha = 1f
                markTopMovies1.alpha = 0.5f
                markTopMovies2.alpha = 0.5f
            }
        }
        topMoviesCarouselAdapter.setData(images)
        carousel.setAdapter(topMoviesCarouselAdapter)

        populateRecyclerView()
        setUpRecyclerView()



    }

    fun setUpRecyclerView(){
        moviesRv.adapter = moviesAdapter
        moviesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
    fun populateRecyclerView(){
        val mockData = listOf(
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
            MoviePoster(imageUrl = "https://upload.wikimedia.org/wikipedia/pt/8/86/I_Care_A_Lot_poster.jpg", imdbId = ""),
        )
        moviesAdapter.setData(mockData)
    }

}