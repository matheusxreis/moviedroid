package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.utils.Constants

class MyListRowBinding {

    companion object {

        @BindingAdapter("defineMyListTitle", "defineMyListAmount", requireAll = true)
        @JvmStatic
        fun defineMyListTitle(textView: TextView, title:String, amount:Int){
            if(amount == 1){
                textView.text = "$title · $amount item"
            }else {
                textView.text = "$title · $amount items"
            }
        }

        @BindingAdapter("loadCover", "hasCover", requireAll = true)
        @JvmStatic
        fun loadCover(imageView: ImageView, imageUrl:String, amount:Int){

            if(amount>0) {
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

                imageView.load("${Constants.IMAGE_BASE_URL}$imageUrl") {
                    crossfade(900)
                    error(R.drawable.no_result)
                    placeholder(shimmerDrawable)
                }
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_no_image))
            }
        }
    }
}