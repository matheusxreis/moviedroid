package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class MoviesRowBinding {

    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView, url:String){
            imageView.load(url)
        }
    }

}