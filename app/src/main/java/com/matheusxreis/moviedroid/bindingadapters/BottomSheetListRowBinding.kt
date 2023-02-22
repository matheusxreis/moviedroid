package com.matheusxreis.moviedroid.bindingadapters

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.asLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.google.android.material.checkbox.MaterialCheckBox
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel

class BottomSheetListRowBinding {

    companion object {

        @BindingAdapter(
            "getListViewModel",
            "getListEntity",
            "getMovie",
            requireAll = true
        )
        @JvmStatic
        fun alreadyOnList(
            view: View,
            listsViewModel: ListsViewModel,
            list: ListEntity,
            movie: MoviePoster
        ) {
            val items = listsViewModel.readListItem(list.id.toString())

            items.observe(view.findViewTreeLifecycleOwner()!!) { listItems ->
                val isOnList = listItems?.find { it.itemId == movie.id }
                Log.d("oops", view.toString())
                when (view) {
                    is CheckBox -> {
                        Log.d("oops", "i'm not a text view")
                        view.isChecked = isOnList != null
                        view.isEnabled = isOnList != null
                        view.isClickable = isOnList != null

                        if (isOnList != null) {
                            view.buttonTintList =
                                ContextCompat.getColorStateList(view.context, R.color.placeholder)
                        }

                    }


                    is TextView -> {
                        if (isOnList == null) {
                            view.visibility = View.INVISIBLE
                        } else {
                            view.visibility = View.VISIBLE
                        }
                    }


                }
            }


        }
    }
}