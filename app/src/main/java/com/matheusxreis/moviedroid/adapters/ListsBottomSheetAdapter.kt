package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListBottomsheetRowLayoutBinding
import kotlinx.android.synthetic.main.list_bottomsheet_row_layout.view.*

class ListsBottomSheetAdapter: RecyclerView.Adapter<ListsBottomSheetAdapter.MyViewHolder>() {

    private var userLists:List<ListEntity> = listOf()
    var selectedLists:ArrayList<ListEntity> = arrayListOf()


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


        holder.itemView.listCardViewBs.setOnClickListener {
            applySelection(holder, currentList)
        }
        holder.itemView.checkboxBs.setOnClickListener {
            applySelection(holder, currentList)
        }

        holder.bind(currentList)
    }

    override fun getItemCount(): Int = userLists.size

    fun setData(newUserLists:List<ListEntity>){
        userLists = newUserLists
        notifyItemInserted(itemCount)
    }


    // Cusotm functions

    fun applySelection(holder: MyViewHolder, currentItem: ListEntity){

        if(selectedLists.contains(currentItem)){
            holder.itemView.checkboxBs.isChecked = false
            selectedLists.remove(currentItem)
        }else {
            holder.itemView.checkboxBs.isChecked = true
            selectedLists.add(currentItem)
        }
    }
}