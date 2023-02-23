package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
import com.matheusxreis.moviedroid.databinding.ContentListRowLayoutBinding

class ContentListAdapter: RecyclerView.Adapter<ContentListAdapter.MyViewHolder>() {

    var items: List<ListItemEntity> = listOf()

    class MyViewHolder(private val binding: ContentListRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ListItemEntity){
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContentListRowLayoutBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<ListItemEntity>) {
        items = newItems
        notifyItemInserted(itemCount)
    }

}