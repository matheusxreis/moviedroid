package com.matheusxreis.moviedroid.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.models.Person
import com.matheusxreis.moviedroid.utils.Constants

class CreditsRowBinding {
    companion object {

        @BindingAdapter("loadCreditImageUrl")
        @JvmStatic
        fun loadCreditImageUrl(imageView: ImageView, url: String? = "") {
            imageView.load("${Constants.IMAGE_BASE_URL}$url") {
                crossfade(900)
                error(R.drawable.no_result_darker)
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