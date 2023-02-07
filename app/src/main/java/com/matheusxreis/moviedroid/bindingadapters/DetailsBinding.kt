package com.matheusxreis.moviedroid.bindingadapters

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.matheusxreis.moviedroid.models.MoviePoster

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
    }
}