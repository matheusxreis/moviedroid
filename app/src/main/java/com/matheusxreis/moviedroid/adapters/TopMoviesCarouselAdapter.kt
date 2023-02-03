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
import com.matheusxreis.moviedroid.models.MoviePoster
import kotlinx.android.synthetic.main.carousel_item_layout.view.*

class TopMoviesCarouselAdapter(): RecyclerView.Adapter<TopMoviesCarouselAdapter.MyViewHolder>() {

    private var topMovies: List<MoviePoster> = emptyList()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item_layout, parent, false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTopMovie = topMovies[position]
        val imageView = holder.itemView.topMoviesImageView
        imageView.load(currentTopMovie.imageUrl)
    }
    override fun getItemCount(): Int = topMovies.size
    fun setData(newTopMovies: List<MoviePoster>){
        topMovies = newTopMovies
        notifyItemInserted(itemCount)
    }

}