package com.matheusxreis.moviedroid.bindingadapters

import MoviesAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel

class SearchResultBinding {

 companion object {

     @BindingAdapter("hasResult")
     @JvmStatic
     fun hasResult(view:View, homeViewModel: HomeViewModel) {
            homeViewModel.searchedResult.observe(view.findViewTreeLifecycleOwner()!!){
                 when(it){
                     is NetworkResult.Error -> {

                         if(view is RecyclerView){
                             view.visibility = View.INVISIBLE
                         }else {
                             view.visibility = View.VISIBLE
                         }

                     }
                     is NetworkResult.Success -> {
                         if(view is TextView || view is ImageView){
                             view.visibility = View.INVISIBLE
                         }else {
                             view.visibility = View.VISIBLE
                         }
                     }
                     else -> {
                        if(view is RecyclerView){
                            view.visibility = View.VISIBLE
                        }else {
                            view.visibility = View.INVISIBLE
                        }
                     }
                 }
            }
     }
 }

}