package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.matheusxreis.moviedroid.utils.Constants

class MoviesRowBinding {

    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, url:String? = ""){
            imageView.load("${Constants.IMAGE_BASE_URL}$url")
        }
    }

}