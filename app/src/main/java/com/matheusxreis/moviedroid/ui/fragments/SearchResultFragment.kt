package com.matheusxreis.moviedroid.ui.fragments

import MoviesAdapter
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_result.*

@AndroidEntryPoint
class SearchResultFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener{

    private val args by navArgs<SearchResultFragmentArgs>()
    private val resultSearchAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var searchView: SearchView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }


    // MENU PROVIDER
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menuInflater.inflate(R.menu.menu_home_fragment, menu)

        val searchQuery = args.searchQuery

        val searchMenu = menu.findItem(R.id.search_menu)

        searchView = searchMenu.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setQuery(searchQuery, false)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == android.R.id.home){
            findNavController().popBackStack()
        }
       return true
    }

    // Text listener

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        searchView.clearFocus()
        return true
    }


    // CUSTOM FUNCTIONS
    private fun setUpRecyclerView(){
        searchResultRv.adapter = resultSearchAdapter
        searchResultRv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun populateRecyclerView(){

    }

    private fun showShimmer(){
        searchResultRv.showShimmer()
    }
    private fun hideShimmer(){
        searchResultRv.hideShimmer()
    }




}