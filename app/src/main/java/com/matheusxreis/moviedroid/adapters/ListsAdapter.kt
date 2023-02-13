package com.matheusxreis.moviedroid.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListRowLayoutBinding
import kotlinx.android.synthetic.main.list_row_layout.view.*

class ListsAdapter(
    private val requireActivity:FragmentActivity
) : RecyclerView.Adapter<ListsAdapter.MyViewModel>(), ActionMode.Callback {

    private var userLists: List<ListEntity> = listOf()

    class MyViewModel(private val binding: ListRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(myList: ListEntity) {
            binding.list = myList

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListRowLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewModel(binding)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        val currentItem = userLists[position]

        if(currentItem.name != "Favorites") {
            holder.itemView.cardViewList.setOnLongClickListener{
                requireActivity.startActionMode(this)

                true
            }
        }

        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = userLists.size


    fun setData(newUserList:List<ListEntity>){
        userLists = newUserList
        notifyDataSetChanged()
    }

    // ACTION MODE

    override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
    ///
    return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {

    return true
    }

    override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {

    return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {

    }
}