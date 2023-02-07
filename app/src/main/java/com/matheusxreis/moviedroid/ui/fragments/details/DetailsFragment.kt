package com.matheusxreis.moviedroid.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    val args by navArgs<DetailsFragmentArgs>()
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


        //return inflater.inflate(R.layout.fragment_details, container, false)

        val layoutInflater = LayoutInflater.from(requireContext())
        val binding = FragmentDetailsBinding.inflate(layoutInflater)

        binding.movie = args.movie

        return binding.root
    }

}