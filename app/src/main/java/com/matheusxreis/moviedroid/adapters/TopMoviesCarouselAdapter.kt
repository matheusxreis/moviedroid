package com.matheusxreis.moviedroid.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.databinding.CarouselItemLayoutBinding
import com.matheusxreis.moviedroid.models.MoviePoster
import kotlinx.android.synthetic.main.carousel_item_layout.view.*

class TopMoviesCarouselAdapter(): RecyclerView.Adapter<TopMoviesCarouselAdapter.MyViewHolder>() {

    private var topMovies: List<MoviePoster> = emptyList()

    class MyViewHolder(private val binding: CarouselItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(topMovie:MoviePoster){
            binding.moviePoster = topMovie
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CarouselItemLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTopMovie = topMovies[position]
        holder.bind(currentTopMovie)
    }
    override fun getItemCount(): Int = topMovies.size
    fun setData(newTopMovies: List<MoviePoster>){
        topMovies = newTopMovies
        notifyItemInserted(itemCount)
    }

}