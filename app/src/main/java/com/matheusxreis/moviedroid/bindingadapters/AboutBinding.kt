package com.matheusxreis.moviedroid.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.facebook.shimmer.ShimmerFrameLayout
import com.matheusxreis.moviedroid.models.Genre
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel

class AboutBinding {
    companion object {

        @BindingAdapter("defineTextViewValue")
        @JvmStatic
        fun defineTextViewValue(textView: TextView, value: String?) {
            textView.text = value
        }

        @BindingAdapter("getGenres")
        @JvmStatic
        fun getGenres(textView: TextView, genres: List<Genre>?) {
            var allGenres: String? = null
            genres?.forEach {
                if (!allGenres.isNullOrEmpty()) {
                    allGenres = "$allGenres, ${it.name}"
                }else {
                    allGenres = it.name
                }
            }
            textView.text = allGenres
        }

        @BindingAdapter("hasDetailsResult")
        @JvmStatic
        fun hasDetailsResult(view: View, detailsViewModel: DetailsViewModel) {

            detailsViewModel.details.observe(view.findViewTreeLifecycleOwner()!!) {
                when (it) {
                    is NetworkResult.Success -> {
                        if(view is ShimmerFrameLayout){
                            view.hideShimmer()
                            view.visibility = View.INVISIBLE
                        }else if(view is TextView){
                            view.visibility = View.VISIBLE
                        }

                    }
                    is NetworkResult.Loading -> {
                        if(view is ShimmerFrameLayout) {
                            view.visibility = View.VISIBLE
                            view.showShimmer(true)
                        }else if(view is TextView){
                            view.visibility = View.INVISIBLE
                        }

                    }
                    is NetworkResult.Error -> {
                        if(view is ShimmerFrameLayout) {
                            view.hideShimmer()
                            view.visibility = View.INVISIBLE
                        }else if(view is TextView){
                            view.visibility = View.VISIBLE
                        }
                    }
                }

            }
        }
    }
}

