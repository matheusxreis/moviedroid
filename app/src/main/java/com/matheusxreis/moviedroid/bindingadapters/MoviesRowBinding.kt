package com.matheusxreis.moviedroid.bindingadapters

import android.content.Intent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
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
            val shimmer = Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(imageView.context, R.color.placeholder))
                .setDuration(700)
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }


            imageView.load("${Constants.IMAGE_BASE_URL}$url") {
                crossfade(900)
                error(R.drawable.no_result)
                placeholder(shimmerDrawable)
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