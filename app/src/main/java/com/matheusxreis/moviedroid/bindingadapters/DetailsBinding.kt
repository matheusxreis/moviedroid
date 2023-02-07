package com.matheusxreis.moviedroid.bindingadapters

import android.graphics.BlurMaskFilter
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.matheusxreis.moviedroid.models.MoviePoster
import androidx.lifecycle.Transformations
import coil.transform.CircleCropTransformation
import com.commit451.coiltransformations.BlurTransformation
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.utils.Constants

class DetailsBinding {
    companion object {

        @BindingAdapter("setVotesValues")
        @JvmStatic
        fun setVotesValues(view: View, moviePoster: MoviePoster) {
            when (view) {
                is RatingBar -> {
                    view.rating = moviePoster.voteAverage / 2
                }
                is TextView -> {
                    view.text = "${moviePoster.vouteCount} votes"
                }
            }
        }

        @BindingAdapter("loadAndBlurImage")
        @JvmStatic
        fun loadAndBlurImage(imageView: ImageView, url: String){
            imageView.load("${Constants.IMAGE_BASE_URL}$url")  {
                transformations(BlurTransformation(imageView.context)) // You can add as many as desired
                crossfade(900)
                error(R.drawable.no_result)
            }
        }
    }
}