package com.matheusxreis.moviedroid.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

class AboutBinding {
    companion object {

        @BindingAdapter("defineTextViewValue")
        @JvmStatic
        fun defineTextViewValue(textView: TextView, value:String?){
            textView.text = value
        }
    }
}