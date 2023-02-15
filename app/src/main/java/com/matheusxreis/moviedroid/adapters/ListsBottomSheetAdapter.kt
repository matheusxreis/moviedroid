package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListBottomsheetRowLayoutBinding

class ListsBottomSheetAdapter: RecyclerView.Adapter<ListsBottomSheetAdapter.MyViewHolder>() {

    private var userLists:List<ListEntity> = listOf()


    class MyViewHolder(private val binding:ListBottomsheetRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userList:ListEntity){
            binding.list = userList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListBottomsheetRowLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentList = userLists[position]
        holder.bind(currentList)
    }

    override fun getItemCount(): Int = userLists.size

    fun setData(newUserLists:List<ListEntity>){
        userLists = newUserLists
        notifyItemInserted(itemCount)
    }
}