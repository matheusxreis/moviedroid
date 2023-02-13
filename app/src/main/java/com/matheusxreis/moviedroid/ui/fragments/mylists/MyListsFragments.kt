package com.matheusxreis.moviedroid.ui.fragments.mylists

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.lifecycle.Lifecycle
import com.matheusxreis.moviedroid.R

class MyListsFragments : Fragment(), MenuProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return inflater.inflate(R.layout.fragment_my_lists_fragments, container, false)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_my_list_fragment, menu)

        menu.forEach {
            setTintIconMenuItem(it)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return true
    }


    // CUSTOM FUNCTIONS

    private fun setTintIconMenuItem(menuItem: MenuItem, color: Int = R.color.white) {
        menuItem.icon?.setTint(ContextCompat.getColor(requireActivity(), color))
    }

}