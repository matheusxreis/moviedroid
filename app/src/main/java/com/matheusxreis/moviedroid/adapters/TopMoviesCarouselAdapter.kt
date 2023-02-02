package com.matheusxreis.moviedroid.adapters

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.Carousel
import coil.load

class TopMoviesCarouselAdapter(val onPopulateCallback: (index:Int) -> Unit): Carousel.Adapter {

    var images = listOf<String>()
    override fun count(): Int = images.size

    override fun populate(view: View?, index: Int) {

        if(view is ImageView){
            view.load(images[index])
        }
        onPopulateCallback(index)
    }

    override fun onNewItem(index: Int) {
            ///
    }

    fun setData(newImages: List<String>){
        images = newImages
    }
}