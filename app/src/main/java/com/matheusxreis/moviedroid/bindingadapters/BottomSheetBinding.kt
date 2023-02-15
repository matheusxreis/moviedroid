package com.matheusxreis.moviedroid.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

class BottomSheetBinding {

    companion object {

        @BindingAdapter("setAmountItems")
        @JvmStatic
        fun setAmountItems(textView: TextView, amount:Int){
            if(amount != 1){
                textView.text = "$amount items"
            }else {
                textView.text = "1 item"
            }
        }
    }
}