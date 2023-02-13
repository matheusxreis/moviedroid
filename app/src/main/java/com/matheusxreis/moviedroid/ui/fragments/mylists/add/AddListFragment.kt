package com.matheusxreis.moviedroid.ui.fragments.mylists.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.viewmodels.ListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_list.*
import kotlinx.android.synthetic.main.video_row_layout.*

@AndroidEntryPoint
class AddListFragment : Fragment() {

    private val myListsViewModel by activityViewModels<ListsViewModel>()
    private lateinit var mView: View
    private val args by navArgs<AddListFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        class Back(enabled: Boolean) : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, Back(true))

        mView = inflater.inflate(R.layout.fragment_add_list, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        desablingButton()
        isEditAction()
        textInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    desablingButton()
                } else {
                    enablingButton()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        addButton.setOnClickListener {
            addNewList()
        }
        cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addNewList() {
        val name = textInput.text.toString()

        if (name.isNullOrEmpty()) {
            textInputLayout.error = "Please, give a name"
        } else {
            if (nameAlreadyExist(name)) {
                textInput.error = "The list already exist."
                textInputLayout.error = ""
                return;
            }
            if (nameIsFavorite(name)) {
                textInput.error = "You already have a favorite list."
                textInputLayout.error = ""
                return;
            }
            if(args.list == null){
                myListsViewModel.addList(name = name)
            }else {
                val list = args.list
                val listUpdated = ListEntity(
                    id = list!!.id,
                    name = name,
                    createdAt = list.createdAt,
                    coverUrl = list.coverUrl,
                    amountItems = list.amountItems
                )
                myListsViewModel.updateListName(listUpdated)
            }
            showSnackbar(name)
            findNavController().popBackStack()
        }
    }

    private fun desablingButton() {
        addButton.isEnabled = false
        addButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.placeholder))
        addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark))
    }

    private fun enablingButton() {
        addButton.isEnabled = true
        addButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
        addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
    }

    private fun nameAlreadyExist(name: String): Boolean {
        val exist = myListsViewModel.lists.value?.find { it.name.trim() == name.trim() }
        return exist != null
    }

    private fun nameIsFavorite(name: String): Boolean {
        if (name.uppercase().trim() == "favorites".uppercase().trim()) {
            return true
        }
        return false
    }

    private fun isEditAction(){
        if(args.list != null){
            textInput.setText(args.list!!.name)
            addButton.text="Edit"
            labelNewListTv.text="Rename your list"
        }
    }

    private fun showSnackbar(name: String){

        val text = when(args.list){
             null -> {
                "The list $name was added"
            }
            else -> {
                "The list $name was updated"
            }
        }
        Snackbar.make(
            mView,
            text,
            Snackbar.LENGTH_SHORT
        )
            .setAction("Okay", {})
            .show()

    }

}


