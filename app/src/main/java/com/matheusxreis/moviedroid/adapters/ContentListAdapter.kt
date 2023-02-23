package com.matheusxreis.moviedroid.adapters

import android.util.Log
import android.view.*
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
import com.matheusxreis.moviedroid.databinding.ContentListRowLayoutBinding
import kotlinx.android.synthetic.main.content_list_row_layout.view.*

class ContentListAdapter
    (private val requireActivity:FragmentActivity,
    private val navController: NavController): RecyclerView.Adapter<ContentListAdapter.MyViewHolder>(),
    ActionMode.Callback {

    var items: List<ListItemEntity> = listOf()

    private var contextualSelectedItems: ArrayList<ListItemEntity> = arrayListOf()
    private val myViewHolders: ArrayList<MyViewHolder> = arrayListOf()
    private lateinit var myActionMode:ActionMode

    class MyViewHolder(private val binding: ContentListRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItemEntity) {
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

        myViewHolders.add(holder)

        holder.itemView.contentCardView.setOnLongClickListener {
            requireActivity.startActionMode(this)

            applySelection(
                holder = holder,
                currentItem = currentItem
            )
            true
        }
        holder.itemView.contentCardView.setOnClickListener {
            if(!contextualSelectedItems.isNullOrEmpty()){
                applySelection(
                    holder = holder,
                    currentItem = currentItem
                )
            }else {
                //
            }

        }
        bindSelection(
            holder = holder,
            currentItem = currentItem
        )
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<ListItemEntity>) {
        items = newItems
        notifyItemInserted(itemCount)
    }

    // Action Mode Callback
    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        myActionMode = actionMode!!
        applyStatusBarColor(R.color.purple_700)

        myActionMode.menuInflater.inflate(R.menu.contextual_menu_action_mode_content, menu)
        menu?.forEach {
            it?.icon?.setTint(
                ContextCompat.getColor(
                    requireActivity.applicationContext,
                    R.color.white
                )
            )
        }
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {

        return true
    }

    override fun onActionItemClicked(p0: ActionMode?, menuItem: MenuItem?): Boolean {

        when(menuItem?.itemId){
            R.id.contextual_delete_content_menu -> {
                Log.d("removing", "removing item")
            }
        }
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        contextualSelectedItems.clear()
        myViewHolders.forEach {
            removeSelectedStyle(
                materialCardView = it.itemView.contentCardView as MaterialCardView,
                it.itemView.checkboxContentSelected
            )
            it.itemView.checkboxContentSelected.visibility = View.INVISIBLE
        }
        applyStatusBarColor(R.color.dark)
    }


    //Custom functions

    private fun applySelection(
        holder: RecyclerView.ViewHolder,
        currentItem: ListItemEntity
    ) {

        when (contextualSelectedItems.contains(currentItem)) {
            true -> {
                contextualSelectedItems.remove(currentItem)
                removeSelectedStyle(
                    materialCardView = holder.itemView.contentCardView as MaterialCardView,
                    checkBox = holder.itemView.checkboxContentSelected
                )
                defineActionModeTitle()
                if (contextualSelectedItems.size == 0) {
                    this.clearContextualAction()
                }
            }
            false -> {
                contextualSelectedItems.add(currentItem)
                applySelectedStyle(
                    materialCardView = holder.itemView.contentCardView as MaterialCardView,
                    checkBox = holder.itemView.checkboxContentSelected
                )
                defineActionModeTitle()

                if (contextualSelectedItems.size == 1) {
                    myViewHolders.forEach {
                        it.itemView.checkboxContentSelected.visibility = View.VISIBLE
                    }
                }
            }
        }

    }
    private fun bindSelection(holder: RecyclerView.ViewHolder, currentItem: ListItemEntity) {

        if (contextualSelectedItems.contains(currentItem)) {
            applySelectedStyle(
                materialCardView = holder.itemView.contentCardView as MaterialCardView,
                checkBox = holder.itemView.checkboxContentSelected
            )

        } else {
            removeSelectedStyle(
                materialCardView = holder.itemView.contentCardView as MaterialCardView,
                checkBox = holder.itemView.checkboxContentSelected
            )
        }

        if (contextualSelectedItems.size > 0) {
            holder.itemView.checkboxContentSelected.visibility = View.VISIBLE
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

    private fun defineActionModeTitle() {
        myActionMode.title = when (contextualSelectedItems.size) {
            0 -> "No items selected"
            1 -> "1 item selected"
            else -> "${contextualSelectedItems.size} items selected"
        }

    }
    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }
    // Disabling action mode
    fun clearContextualAction() {
        if (this::myActionMode.isInitialized) {
            myActionMode.finish()
        }
    }

}