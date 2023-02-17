package com.matheusxreis.moviedroid.ui.fragments.details.mylistbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.ListsBottomSheetAdapter
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import kotlinx.android.synthetic.main.fragment_my_lists_bottom_sheet.*


class MyListsBottomSheet : BottomSheetDialogFragment() {

    private val mAdapter: ListsBottomSheetAdapter by lazy {
        ListsBottomSheetAdapter()
    }
    private val myListsViewModel:ListsViewModel by activityViewModels<ListsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_lists_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        populateRecyclerView()

        val behavior: BottomSheetBehavior<*> = (dialog as BottomSheetDialog).behavior
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                buttonLayout.y = ((bottomSheet.parent as View).height - bottomSheet.top - buttonLayout.height).toFloat()
            }
        }.apply {
          view.post { onSlide(view.parent as View, 0f) }
        })

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

}