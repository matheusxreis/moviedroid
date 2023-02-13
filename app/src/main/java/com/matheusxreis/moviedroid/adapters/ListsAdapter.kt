package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListRowLayoutBinding

class ListsAdapter : RecyclerView.Adapter<ListsAdapter.MyViewModel>() {

    private var userLists: List<ListEntity> = listOf()

    class MyViewModel(private val binding: ListRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myList: ListEntity) {
            binding.list = myList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListRowLayoutBinding.inflate(layoutInflater)
        return MyViewModel(binding)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        val currentItem = userLists[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = userLists.size


    fun setData(newUserList:List<ListEntity>){
        userLists = newUserList
        notifyItemInserted(itemCount)
    }
}