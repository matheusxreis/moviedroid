package com.matheusxreis.moviedroid.bindingadapters

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.utils.Constants

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

            val url = "https://i.ytimg.com/vi/${videoKey}/hqdefault.jpg"
            imageView.load(url) {
                error(R.drawable.no_result)
                placeholder(shimmerDrawable)
            }
            imageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${Constants.YOUTUBE_BASE_URL}${videoKey}"))
                imageView.context.startActivity(intent)
            }
        }

    }
}