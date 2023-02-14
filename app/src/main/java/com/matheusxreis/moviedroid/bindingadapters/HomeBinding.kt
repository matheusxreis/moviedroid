package com.matheusxreis.moviedroid.bindingadapters

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel

import kotlinx.coroutines.launch

class HomeBinding {

    companion object {

        @BindingAdapter("isNetworkAvailable", "isNotNetView", requireAll = true)
        @JvmStatic
        fun isNetworkAvailable(
            view:View,
            homeViewModel: HomeViewModel,
            isNotNetView: Boolean
        ){


          homeViewModel.viewModelScope.launch {
              homeViewModel.networkListener.checkNetworkavailability().collect {

                 Log.d("isnotview", isNotNetView.toString())
                  if(isNotNetView){
                        when(it){
                            false -> {
                                view.visibility = View.VISIBLE
                            }
                            true -> {
                                view.visibility = View.INVISIBLE
                            }
                        }
                  }else {
                      when(it){
                          false -> {
                              view.visibility = View.INVISIBLE
                          }
                          true -> {
                              view.visibility = View.VISIBLE
                          }
                      }
                  }
              }
          }

        }
    }
}