package com.matheusxreis.moviedroid.adapters

import android.view.*
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
        myViewHolders.add(holder)
        val currentItem = userLists[position]

        if (currentItem.name != "Favorites") {
            selectItem(holder, currentItem)
            if(contextualSelectedLists.size>0){
                applyCheckbox(applyVisibility = true)
            }
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
            removeSelectedStyle(it.itemView.cardViewList as MaterialCardView)
        }
        hideCheckbox()
        applyStatusBarColor(R.color.dark)
    }

    // CUSTOM FUNCTIONS

    //*****  ADD AND REMOVE ARRAY //
    // dinamic title action mode
    private fun defineActionModeTitle() {
        myActionMode.title = when (contextualSelectedLists.size) {
            0 -> "No items selected"
            1 -> "1 item selected"
            else -> "${contextualSelectedLists.size} items selected"
        }

    }

    // clicks listeners
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

                handleItem(
                    currentItem = currentItem,
                    materialCardView = it.cardViewList as MaterialCardView
                )
            } else {
                ///
            }
        }
    }


    // decide if add or remove from selected list
    private fun handleItem(currentItem: ListEntity, materialCardView: MaterialCardView) {
        val isInList = contextualSelectedLists.contains(currentItem)

        if (isInList) {
            removeItem(currentItem)
            removeSelectedStyle(materialCardView)
        } else {
            addItem(currentItem)
            applySelectedStyle(materialCardView)
        }
        defineActionModeTitle()

    }

    // add item in array selected list
    private fun addItem(item: ListEntity) {

        contextualSelectedLists.add(item)
        changeEditMenuVisible()
    }

    // remove item from array selected list
    private fun removeItem(item: ListEntity) {
        contextualSelectedLists.remove(item)

        if (contextualSelectedLists.size == 0) {
            clearContextualAction()

        }
        changeEditMenuVisible()
    }

    //*****  STYLES //

    // changing edit menu visible according with amount of selected items
    private fun changeEditMenuVisible() {

        if (contextualSelectedLists.size != 1) {
            editMenuItem.isVisible = false
            editMenuItem.isEnabled = false
        } else {
            editMenuItem.isVisible = true
            editMenuItem.isEnabled = true
        }
    }

    // change styles when item is selected
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
        applyCheckbox()
    }

    // remove styles when item is not selected
    private fun removeSelectedStyle(materialCardView: MaterialCardView) {
        materialCardView.strokeWidth = 0
        materialCardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity.applicationContext,
                R.color.text
            )
        )
        applyCheckbox(applyVisibility = false)
    }


    private fun applyCheckbox(
        applyVisibility: Boolean = true,
    ) {
        myViewHolders.forEach {  holder ->
            val checkBox = holder.itemView.checkboxListSelected

            val isFavorite = holder.itemView.titleListTv.text.toString().uppercase() == "Favorites".uppercase()
            if (applyVisibility && !isFavorite) {
                checkBox.visibility = View.VISIBLE
            }
            if (holder.bindingAdapterPosition >= 0) {

                val currentItem = userLists[holder.bindingAdapterPosition]
                checkBox.isChecked =
                    contextualSelectedLists.contains(currentItem)
                checkBox.setOnClickListener {
                    handleItem(
                        currentItem = currentItem,
                        materialCardView = holder.itemView.cardViewList as MaterialCardView
                    )
                }
            }
        }
    }

    private fun hideCheckbox() {
        myViewHolders.forEach {
            it.itemView.checkboxListSelected.visibility = View.INVISIBLE
        }
    }

    // change status bar color
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