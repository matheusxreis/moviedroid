package com.matheusxreis.moviedroid.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.matheusxreis.moviedroid.models.Genre

class AboutBinding {
    companion object {

        @BindingAdapter("defineTextViewValue")
        @JvmStatic
        fun defineTextViewValue(textView: TextView, value:String?){
            textView.text = value
        }

        @BindingAdapter("getGenres")
        @JvmStatic
        fun getGenres(textView: TextView, genres:List<Genre>?){
           var allGenres:String? = null
           genres?.forEach {
               if(!allGenres.isNullOrEmpty()){
                   allGenres = "$allGenres, ${it.name}"
               }
               allGenres = it.name
           }
            textView.text = allGenres
        }
    }
}