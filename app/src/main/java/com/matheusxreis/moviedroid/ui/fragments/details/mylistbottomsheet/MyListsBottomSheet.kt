package com.matheusxreis.moviedroid.ui.fragments.details.mylistbottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.ListsBottomSheetAdapter
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import kotlinx.android.synthetic.main.fragment_my_lists_bottom_sheet.*


class MyListsBottomSheet : BottomSheetDialogFragment() {

    private val args by navArgs<MyListsBottomSheetArgs>()
    private val myListsViewModel:ListsViewModel by activityViewModels<ListsViewModel>()
    private val mAdapter: ListsBottomSheetAdapter by lazy {
        ListsBottomSheetAdapter(
            movie = args.item,
            listsViewModel = myListsViewModel
        )
    }
    private lateinit var mView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView = inflater.inflate(R.layout.fragment_my_lists_bottom_sheet, container, false)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myListsViewModel.lists.observe(viewLifecycleOwner){
            if(it.size==1){
                noListImageView.visibility = View.VISIBLE
                bottomSheetSubtitleTv.text = "Looks like as you don't have any list"
            }else {
                noListImageView.visibility = View.INVISIBLE
                bottomSheetSubtitleTv.text = bottomSheetSubtitleTv.text
            }
        }
        val behavior: BottomSheetBehavior<*> = (dialog as BottomSheetDialog).behavior
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                buttonLayout.y = ((bottomSheet.parent as View).height - bottomSheet.top - buttonLayout.height).toFloat()

            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                buttonLayout.y = ((bottomSheet.parent as View).height - bottomSheet.top - buttonLayout.height).toFloat()
            }
        }.apply {
            view.post { onSlide(view.parent as View, 0f);
                }
        })
        behavior.state = BottomSheetBehavior.STATE_EXPANDED


        setUpRecyclerView()
        populateRecyclerView()
        addInList()


    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }
    // CUSTOM FUNCTIONS

    fun setUpRecyclerView(){

        bottomSheetRecyclerView.adapter = mAdapter
        bottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }
    fun populateRecyclerView(){
        myListsViewModel.lists.observe(viewLifecycleOwner){
           if(it.size>1) {
               val data = it.slice(1..it.lastIndex)
               mAdapter.setData(data)
           }
        }
    }

    private fun addInList(){
        confirmButton.setOnClickListener {
            if(mAdapter.selectedLists.isNotEmpty()){
                var type= "tv"
                val item = args.item
                item.let {
                    if (it.firstAirDate.isNullOrEmpty()) {
                        type = "movie"
                    }
                }
                mAdapter.selectedLists.forEach { list ->
                    val type = type
                        val listItemEntity = ListItemEntity(
                            title = item.title,
                            itemId = item.id,
                            imageUrl = item.imageUrl.toString(),
                            overview = "",
                            type = type,
                            rating = item.voteAverage / 2,
                            listCode = list.id.toString()
                        )
                        myListsViewModel.addListItem(listItemEntity)

                        val listEntity = ListEntity(
                            id = list.id,
                            name=list.name,
                            createdAt = list.createdAt,
                            coverUrl = item.imageUrl?:"",
                            amountItems = list.amountItems+1
                        )
                        myListsViewModel.updateListValue(
                            listEntity = listEntity
                        )
                }

                Toast.makeText(
                    requireContext(),
                    "Added ${args.item.title} in ${mAdapter.selectedLists.size} lists",
                    Toast.LENGTH_SHORT
                )
                    .show()
                this.dismiss()

            }

        }
    }

}