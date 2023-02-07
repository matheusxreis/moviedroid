package com.matheusxreis.moviedroid.bindingadapters

import android.content.Intent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.google.gson.Gson
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.ui.MainActivity
import com.matheusxreis.moviedroid.ui.fragments.home.HomeFragmentDirections
import com.matheusxreis.moviedroid.utils.Constants

class MoviesRowBinding {

    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, url: String? = "") {
            imageView.load("${Constants.IMAGE_BASE_URL}$url") {
                crossfade(900)
                error(R.drawable.no_result)
            }
        }

        @BindingAdapter("goToDetails")
        @JvmStatic
        fun goToDetails(imageView: ImageView, moviePoster: MoviePoster) {

            imageView.setOnClickListener {
                try {

                    val action =
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                            movie = moviePoster,
                            fromSearch = false
                        )
                    imageView.findNavController().navigate(action)
                } catch (e: Exception) {
                    val intent = Intent(imageView.context, MainActivity::class.java)
                    intent.putExtra("key", Gson().toJson(moviePoster))
                    imageView.context.startActivity(intent)
                }

            }
        }
    }

}