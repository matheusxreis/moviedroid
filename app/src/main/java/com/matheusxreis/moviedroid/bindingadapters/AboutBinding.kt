package com.matheusxreis.moviedroid.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.facebook.shimmer.ShimmerFrameLayout
import com.matheusxreis.moviedroid.models.Genre
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel
import com.todkars.shimmer.ShimmerRecyclerView

class AboutBinding {
    companion object {

        @BindingAdapter("defineTextViewValue", "defineTextViewIntValue", requireAll = false)
        @JvmStatic
        fun defineTextViewValue(textView: TextView, value: String?, value2:Int?) {
            if(value!=null) {
                textView.text = value
            }
            if(value2!=null){
                textView.text = value2.toString()
            }
        }

        @BindingAdapter("isTvShowView")
        @JvmStatic
        fun isTvShowView(view: TextView, value:Int?) {
            if(value==null|| value==0){
                view.visibility = View.GONE
            }else {
                view.visibility = View.VISIBLE
            }
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
                        }else if(view is ShimmerRecyclerView){
                            view.hideShimmer()
                        }

                    }
                    is NetworkResult.Loading -> {
                        if(view is ShimmerFrameLayout) {
                            view.visibility = View.VISIBLE
                            view.showShimmer(true)
                        }else if(view is TextView){
                            view.visibility = View.INVISIBLE
                        }else if(view is ShimmerRecyclerView){
                            view.showShimmer()
                        }

                    }
                    is NetworkResult.Error -> {
                        if(view is ShimmerFrameLayout) {
                            view.hideShimmer()
                            view.visibility = View.INVISIBLE
                        }else if(view is TextView){
                            view.visibility = View.VISIBLE
                        }else if(view is ShimmerRecyclerView){
                            view.hideShimmer()
                        }
                    }
                }

            }
        }
    }
}

