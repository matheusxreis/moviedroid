package com.matheusxreis.moviedroid.ui.fragments.mylists

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.ListsAdapter
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_my_lists_fragments.*

@AndroidEntryPoint
class MyListsFragments : Fragment(), MenuProvider {

    private lateinit var mView:View

    private val myListsViewModel:ListsViewModel by activityViewModels<ListsViewModel>()
    private val mAdapter by lazy {
        ListsAdapter(
            requireActivity = requireActivity(),
            myListViewModel = myListsViewModel,
            navController = findNavController()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        mView = inflater.inflate(R.layout.fragment_my_lists_fragments, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        populateRecyclerView()
    }

    override fun onDestroy() {
        mAdapter.clearContextualAction()
        super.onDestroy()
    }

    // MENU PROVIDER

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_my_list_fragment, menu)

        menu.forEach {
            setTintIconMenuItem(it)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        when(menuItem.itemId){
            R.id.add_list_menu -> {
                val action = MyListsFragmentsDirections.actionMyListsFragments2ToAddListFragment()
                findNavController().navigate(action)
            }
            R.id.delete_all_lists -> {
                myListsViewModel.deleteAllLists()
                Snackbar.make(
                    mView,
                    "Deleted all lists", Snackbar.LENGTH_SHORT
                )
                    .setAction("Okay", {})
                    .show()
            }
        }
       return true
    }


    // CUSTOM FUNCTIONS

    private fun setTintIconMenuItem(menuItem: MenuItem, color: Int = R.color.white) {
        menuItem.icon?.setTint(ContextCompat.getColor(requireActivity(), color))
    }

    private fun setUpRecyclerView(){
        myListsRecyclerView.adapter = mAdapter
        myListsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
    private fun populateRecyclerView(){
        myListsViewModel.lists.observe(viewLifecycleOwner){
           if(!it.isNullOrEmpty()){
               mAdapter.setData(it)
           }else {
               createFavorites()
           }
        }
    }
    private fun createFavorites(){
        myListsViewModel.addList(
            "Favorites"
        )
    }


}