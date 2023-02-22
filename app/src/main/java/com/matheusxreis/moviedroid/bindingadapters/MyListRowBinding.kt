package com.matheusxreis.moviedroid.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.utils.Constants
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import kotlinx.coroutines.launch


class MyListRowBinding {

    companion object {

        @BindingAdapter("defineMyListTitle")
        @JvmStatic
        fun defineMyListTitle(textView: TextView, title: String) {
            textView.text = "$title"
        }

        @BindingAdapter("defineMyListAmountFavorite", "listIdFav", requireAll = true)
        @JvmStatic
        fun defineMyListAmountFavorite(
            textView: TextView,
            myListsViewModel: ListsViewModel,
            id: Int
        ) {

            if (id != 1) {
                return; }
            myListsViewModel.viewModelScope.launch {
                myListsViewModel.favorites.collect { it ->
                    val values = myListsViewModel.lists.value?.find { it.id == 1 }
                    if (values != null && it.isNotEmpty()) {
                        val last = it.sortedBy { favorite -> favorite.id }.last()
                        val favoriteList = ListEntity(
                            id = values!!.id,
                            name = values!!.name,
                            amountItems = it.size,
                            coverUrl = last.imageUrl,
                            createdAt = values!!.createdAt
                        )
                        if (favoriteList != values) {
                            myListsViewModel.updateFavoritesValues(
                                favoriteList = favoriteList
                            )
                        }
                        if (favoriteList.amountItems != 1) {
                            textView.text = "${favoriteList.amountItems} items"
                        } else {
                            textView.text = "1 item"
                        }
                    } else {
                        textView.text = "0 items"
                        if (values != null) {
                            val favoriteList = ListEntity(
                                id = values!!.id,
                                name = values!!.name,
                                amountItems = it.size,
                                coverUrl = "",
                                createdAt = values!!.createdAt
                            )
                            if (favoriteList != values) {
                                myListsViewModel.updateFavoritesValues(
                                    favoriteList = favoriteList
                                )
                            }
                        }
                    }
                }
            }
        }

        @BindingAdapter("defineMyListAmount", "listId", requireAll = true)
        @JvmStatic
        fun defineMyListAmount(textView: TextView, myListsViewModel: ListsViewModel, id: Int) {
            if (id == 1) {
                return; }
            myListsViewModel.lists.observe(textView.findViewTreeLifecycleOwner()!!) {

                val currentList = it.find { it.id == id }
                val currentAmount = currentList?.amountItems ?: 0
                if (currentAmount != 1) {
                    textView.text = "$currentAmount items"
                } else {
                    textView.text = "1 item"
                }

            }

        }

        @BindingAdapter("loadCover", "hasCover", requireAll = true)
        @JvmStatic
        fun loadCover(imageView: ImageView, imageUrl: String, amount: Int) {

            if (amount > 0) {
                val shimmer = Shimmer.ColorHighlightBuilder()
                    .setBaseColor(ContextCompat.getColor(imageView.context, R.color.placeholder))
                    .setDuration(700)
                    .setHighlightAlpha(0.6f) // the shimmer alpha amount
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()
                val shimmerDrawable = ShimmerDrawable().apply {
                    setShimmer(shimmer)
                }

                imageView.load("${Constants.IMAGE_BASE_URL}$imageUrl") {
                    crossfade(900)
                    error(R.drawable.no_result)
                    placeholder(shimmerDrawable)
                }
            } else {
                imageView.load(ContextCompat.getDrawable(imageView.context, R.drawable.ic_no_image))
            }
        }


    }
}