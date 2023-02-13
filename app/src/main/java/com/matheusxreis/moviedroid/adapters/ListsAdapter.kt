package com.matheusxreis.moviedroid.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListRowLayoutBinding
import kotlinx.android.synthetic.main.list_row_layout.view.*

class ListsAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<ListsAdapter.MyViewHolder>(), ActionMode.Callback {

    private var contextualSelectedLists: ArrayList<ListEntity> = arrayListOf()
    private var userLists: List<ListEntity> = listOf()
    private lateinit var myActionMode: ActionMode
    private var myViewHolders: ArrayList<MyViewHolder> = arrayListOf()

    class MyViewHolder(private val binding: ListRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(myList: ListEntity) {
            binding.list = myList

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListRowLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        val currentItem = userLists[position]

        if (currentItem.name != "Favorites") {
            selectItem(holder, currentItem)
        }

        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = userLists.size


    fun setData(newUserList: List<ListEntity>) {
        userLists = newUserList
        notifyDataSetChanged()
    }

    // ACTION MODE

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        ///

        myActionMode = actionMode!!
        myActionMode?.menuInflater?.inflate(R.menu.contextual_menu_action_mode, menu)

        applyStatusBarColor(R.color.purple_700)
        menu?.findItem(R.id.contextual_delete_list_menu)?.icon?.setTint(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.white
            )
        )
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {

        return true
    }

    override fun onActionItemClicked(p0: ActionMode?, menuItem: MenuItem?): Boolean {

        when (menuItem?.itemId) {

        }
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        contextualSelectedLists.clear()
        myViewHolders.forEach {
            removeSelectedStyle(it.itemView.cardViewList as MaterialCardView)
        }
        applyStatusBarColor(R.color.dark)
    }


    private fun defineActionModeTitle() {
        myActionMode.title = when (contextualSelectedLists.size) {
            0 -> "No items selected"
            1 -> "1 item selected"
            else -> "${contextualSelectedLists.size} items selected"
        }

    }

    private fun selectItem(holder: MyViewHolder, currentItem: ListEntity) {
        holder.itemView.cardViewList.setOnLongClickListener {

            if (contextualSelectedLists.size == 0) {
                requireActivity.startActionMode(this)
                addItem(currentItem)
                applySelectedStyle(it as MaterialCardView)
                defineActionModeTitle()
            }

            true
        }
        holder.itemView.cardViewList.setOnClickListener {
            val isOnContextualActionMode = contextualSelectedLists.size > 0
            if (isOnContextualActionMode) {

                val isInList = contextualSelectedLists.contains(currentItem)
                if (isInList) {
                    removeItem(currentItem)
                    removeSelectedStyle(it as MaterialCardView)
                } else {
                    addItem(currentItem)
                    applySelectedStyle(it as MaterialCardView)

                }
                defineActionModeTitle()
            }
        }
    }

    private fun addItem(item: ListEntity) {
        contextualSelectedLists.add(item)
    }

    private fun removeItem(item: ListEntity) {
        contextualSelectedLists.remove(item)

        if (contextualSelectedLists.size == 0) {
            clearContextualAction()
        }
    }

    private fun applySelectedStyle(materialCardView: MaterialCardView) {
        materialCardView.strokeWidth = 2
        materialCardView.strokeColor =
            ContextCompat.getColor(requireActivity.applicationContext, R.color.purple_200)
        materialCardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.text
            )
        )
    }

    private fun removeSelectedStyle(materialCardView: MaterialCardView) {
        materialCardView.strokeWidth = 0
        materialCardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.text
            )
        )
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun clearContextualAction(){
        if(this::myActionMode.isInitialized){
            myActionMode.finish()
        }
    }
}