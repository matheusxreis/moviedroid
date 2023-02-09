package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.models.Person
import com.matheusxreis.moviedroid.utils.Constants

class CreditsRowBinding {
    companion object {

        @BindingAdapter("loadCreditImageUrl")
        @JvmStatic
        fun loadCreditImageUrl(imageView: ImageView, url: String? = "") {

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
                error(R.drawable.no_result_darker)
                placeholder(shimmerDrawable)
            }
        }
        @BindingAdapter("getSubtitle")
        @JvmStatic
        fun getSubtitle(textView: TextView, person: Person){
            if(person.character != null){
                textView.text = "as ${person.character}"
            }else {
                textView.text = person.knownFor
            }
        }
    }
}