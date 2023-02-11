package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.matheusxreis.moviedroid.R

class VideoRowBinding {

    companion object {

        @BindingAdapter("setVideoId")
        @JvmStatic
        fun setVideoId(imageView: ImageView, videoKey: String) {
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

            val url = "https://i.ytimg.com/vi/${videoKey}/maxresdefault.jpg"
            imageView.load(url) {

                error(R.drawable.no_result)
                placeholder(shimmerDrawable)
            }
        }

    }
}