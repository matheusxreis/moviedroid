package com.matheusxreis.moviedroid.bindingadapters

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.asLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
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
            textView: TextView,
            listsViewModel: ListsViewModel,
            list: ListEntity,
            movie: MoviePoster
        ) {
            val items = listsViewModel.readListItem(list.id.toString())

            items.observe(textView.findViewTreeLifecycleOwner()!!) { listItems ->
                val isOnList = listItems?.find { it.itemId == movie.id }
                if (isOnList == null) {
                    textView.visibility = View.INVISIBLE
                } else {
                    textView.visibility = View.VISIBLE
                }
            }

        }
    }
}