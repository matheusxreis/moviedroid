package com.matheusxreis.moviedroid.adapters

import android.view.*
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.databinding.ListRowLayoutBinding
import com.matheusxreis.moviedroid.ui.fragments.mylists.MyListsFragmentsDirections
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import kotlinx.android.synthetic.main.list_row_layout.*
import kotlinx.android.synthetic.main.list_row_layout.view.*

class ListsAdapter(
    private val requireActivity: FragmentActivity,
    private val myListViewModel: ListsViewModel,
    private val navController: NavController
) : RecyclerView.Adapter<ListsAdapter.MyViewHolder>(), ActionMode.Callback {

    private var contextualSelectedLists: ArrayList<ListEntity> = arrayListOf()
    private var userLists: List<ListEntity> = listOf()
    private lateinit var myActionMode: ActionMode
    private var myViewHolders: ArrayList<MyViewHolder> = arrayListOf()
    private lateinit var editMenuItem: MenuItem

    class MyViewHolder(
        private val binding: ListRowLayoutBinding,
        private val myListViewModel: ListsViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(myList: ListEntity) {
            binding.list = myList
            binding.myListViewModel = myListViewModel

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListRowLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding, myListViewModel)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userLists[position]

        if(currentItem.name != "Favorites"){
            myViewHolders.add(holder)
        }

        holder.itemView.cardViewList.setOnLongClickListener {


            if (contextualSelectedLists.size == 0) {
                applySelection(
                    holder = holder,
                    currentItem = currentItem,
                    startActionMode = { requireActivity.startActionMode(this) }
                )

            }
            true
        }
        holder.itemView.cardViewList.setOnClickListener {
            val isOnContextualActionMode = contextualSelectedLists.size > 0
            if (isOnContextualActionMode) {
                applySelection(
                    holder = holder,
                    currentItem = currentItem
                )
            } else {
                ///
            }
        }
        holder.itemView.checkboxListSelected.setOnClickListener {
            applySelection(
                holder = holder,
                currentItem = currentItem
            )

        }

        bindSelection(
            holder = holder,
            currentItem = currentItem
        )
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = userLists.size

    fun setData(newUserList: List<ListEntity>) {
        userLists = newUserList
        notifyDataSetChanged()
    }

    fun applySelection(
        holder: MyViewHolder,
        currentItem: ListEntity,
        startActionMode: () -> Unit = {}
    ) {

        if (currentItem.name == "Favorites") { return; }

        startActionMode()
        if (contextualSelectedLists.contains(currentItem)) {
            contextualSelectedLists.remove(currentItem)
            removeSelectedStyle(
                materialCardView = holder.itemView.cardViewList as MaterialCardView,
                checkBox =  holder.itemView.checkboxListSelected)
            defineActionModeTitle()
            changeEditMenuVisible()

            if (contextualSelectedLists.size == 0) {
                this.clearContextualAction()
            }

        }
        else {
            contextualSelectedLists.add(currentItem)
            applySelectedStyle(
                        materialCardView = holder.itemView.cardViewList as MaterialCardView,
                        checkBox =  holder.itemView.checkboxListSelected)
            defineActionModeTitle()
            changeEditMenuVisible()
            myViewHolders.forEach {
                    it.itemView.checkboxListSelected.visibility = View.VISIBLE
            }


        }

    }

    fun bindSelection(holder: MyViewHolder, currentItem: ListEntity) {
        if (currentItem.name == "Favorites") { return; }

        // called inside bind of view holder to apply correct styles in the card view
        // bcs rv behavior recycles the items
        if (contextualSelectedLists.contains(currentItem)) {
            applySelectedStyle(
                materialCardView =  holder.itemView.cardViewList as MaterialCardView,
                checkBox = holder.itemView.checkboxListSelected
                )

        } else {
            removeSelectedStyle(
                materialCardView = holder.itemView.cardViewList as MaterialCardView,
                checkBox = holder.itemView.checkboxListSelected)
        }

        if(contextualSelectedLists.size>0){
            myViewHolders.forEach {
                    it.itemView.checkboxListSelected.visibility = View.VISIBLE
            }
        }

    }

    // ACTION MODE

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        ///

        myActionMode = actionMode!!
        myActionMode?.menuInflater?.inflate(R.menu.contextual_menu_action_mode, menu)

        applyStatusBarColor(R.color.purple_700)
        menu?.forEach {
            it?.icon?.setTint(
                ContextCompat.getColor(
                    requireActivity.applicationContext,
                    R.color.white
                )
            )
            if (it.itemId == R.id.contextual_edit_list_menu) {
                editMenuItem = it
            }
        }
        return true

    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {

        return true
    }

    override fun onActionItemClicked(p0: ActionMode?, menuItem: MenuItem?): Boolean {

        when (menuItem?.itemId) {
            R.id.contextual_delete_list_menu -> {
                removeAllItems()
            }
            R.id.contextual_edit_list_menu -> {
                editItem(contextualSelectedLists[0])
                clearContextualAction()
            }
        }
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        contextualSelectedLists.clear()
        myViewHolders.forEach {
            removeSelectedStyle(
                materialCardView = it.itemView.cardViewList as MaterialCardView,
                it.itemView.checkboxListSelected
                )
            it.itemView.checkboxListSelected.visibility = View.INVISIBLE
        }
        applyStatusBarColor(R.color.dark)
    }

    // CUSTOM FUNCTIONS

    // dinamic title action mode
    private fun defineActionModeTitle() {
        myActionMode.title = when (contextualSelectedLists.size) {
            0 -> "No items selected"
            1 -> "1 item selected"
            else -> "${contextualSelectedLists.size} items selected"
        }

    }

    private fun changeEditMenuVisible() {

        if (contextualSelectedLists.size != 1) {
            editMenuItem.isVisible = false
            editMenuItem.isEnabled = false
        } else {
            editMenuItem.isVisible = true
            editMenuItem.isEnabled = true
        }
    }

    private fun applySelectedStyle(materialCardView: MaterialCardView, checkBox: CheckBox) {
        materialCardView.strokeWidth = 2
        materialCardView.strokeColor =
            ContextCompat.getColor(requireActivity.applicationContext, R.color.purple_200)
        materialCardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.text
            )
        )
        checkBox.isChecked = true

    }

    private fun removeSelectedStyle(materialCardView: MaterialCardView, checkBox: CheckBox) {
        materialCardView.strokeWidth = 0
        materialCardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.text
            )
        )
        checkBox.isChecked = false
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    // UPDATE/REMOVE ITEM FROM ROOM

    // go to edit page
    private fun editItem(item: ListEntity) {
        val action = MyListsFragmentsDirections.actionMyListsFragments2ToAddListFragment(
            list = item
        )
        contextualSelectedLists.clear()
        navController.navigate(action)
    }

    // remove all items at once
    private fun removeAllItems() {
        contextualSelectedLists.forEach {
            myListViewModel.deleteList(it.id.toString())

        }

        Snackbar.make(
            requireActivity.cardViewList,
            "${contextualSelectedLists.size} ${if (contextualSelectedLists.size > 1) "lists" else "list"} deleted",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Okay", {})
            .show()
        contextualSelectedLists.clear()
        clearContextualAction()
    }

    // disable contextual action mode
    fun clearContextualAction() {
        if (this::myActionMode.isInitialized) {

            myActionMode.finish()
        }
    }
}