package com.matheusxreis.moviedroid.ui.fragments.mylistscontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.ContentListAdapter
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import kotlinx.android.synthetic.main.fragment_my_lists_content.*

class MyListsContent : Fragment() {

    val mAdapter: ContentListAdapter by lazy {
        ContentListAdapter(
            requireActivity = requireActivity(),
            navController = findNavController()
        )
    }
    val myListsViewModel:ListsViewModel by activityViewModels<ListsViewModel>()
    val args by navArgs<MyListsContentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onDestroy() {
        mAdapter.clearContextualAction()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        class Back(enabled: Boolean) : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, Back(true))

        return inflater.inflate(R.layout.fragment_my_lists_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        populateRecyclerView()
    }

    private fun setUpRecyclerView(){
        contentListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentListRecyclerView.adapter = mAdapter
    }
    private fun populateRecyclerView(){
        myListsViewModel.readListItem(args.listId.toString()).observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                mAdapter.setData(it)
            }
        }
    }


}