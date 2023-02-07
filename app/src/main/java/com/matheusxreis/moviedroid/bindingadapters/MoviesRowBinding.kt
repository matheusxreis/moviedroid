package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.ui.fragments.home.HomeFragmentDirections
import com.matheusxreis.moviedroid.utils.Constants

class MoviesRowBinding {

    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, url:String? = ""){
            imageView.load("${Constants.IMAGE_BASE_URL}$url") {
                crossfade(900)
                error(R.drawable.no_result)
            }
        }
        @BindingAdapter("goToDetails")
        @JvmStatic
        fun goToDetails(imageView: ImageView, moviePoster: MoviePoster){

                imageView.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie = moviePoster)
                    imageView.findNavController().navigate(action)

            }
        }
    }

}