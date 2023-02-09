package com.matheusxreis.moviedroid.bindingadapters

import MoviesAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.utils.NetworkStatus
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel

class SearchResultBinding {

    companion object {

        @BindingAdapter("hasResult")
        @JvmStatic
        fun hasResult(view: View, homeViewModel: HomeViewModel) {

            val isConnected = NetworkStatus().hasInternetConnection(view.context)
            homeViewModel.searchedResult.observe(view.findViewTreeLifecycleOwner()!!) {
                when (it) {
                    is NetworkResult.Error -> {

                        if (view is RecyclerView) {
                            view.visibility = View.INVISIBLE
                        } else {
                            when (view) {
                                is TextView -> {
                                    if (!isConnected) {
                                        view.text = "No internet"
                                    } else {
                                         view.text = "Nothing was found"
                                    }
                                }
                                is ImageView -> {
                                    if (!isConnected) {
                                        view.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                view.context,
                                                R.drawable.ic_no_network
                                            )
                                        )
                                    } else {
                                        view.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                view.context,
                                                R.drawable.ic_search_off
                                            )
                                        )

                                    }
                                }
                            }
                            view.visibility = View.VISIBLE
                        }

                    }
                    is NetworkResult.Success -> {
                        if (view is TextView || view is ImageView) {
                            view.visibility = View.INVISIBLE
                        } else {
                            view.visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        if (view is RecyclerView) {
                            view.visibility = View.VISIBLE
                        } else {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

}