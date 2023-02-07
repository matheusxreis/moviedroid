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
import com.google.android.material.tabs.TabLayoutMediator
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.DetailsPagerAdapter
import com.matheusxreis.moviedroid.databinding.FragmentDetailsBinding
import com.matheusxreis.moviedroid.ui.fragments.about.AboutFragment

class DetailsFragment : Fragment() {

    val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        class Back(enabled: Boolean) : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                handleComeBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, Back(true))


        val layoutInflater = LayoutInflater.from(requireContext())
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        binding.movie = args.movie

        setUpAndPopulateViewPager()


        return binding.root
    }


    // CUSTOM FUNCTIONS

    private fun setUpAndPopulateViewPager() {

        val fragments = ArrayList<Fragment>()
        fragments.add(AboutFragment())
        fragments.add(AboutFragment())

        val titles = ArrayList<String>()
        titles.add("About")
        titles.add("Recommended")

        val resultBundle = Bundle()
        resultBundle.putParcelable("movie", args.movie)

        val adapter = DetailsPagerAdapter(
            resultBundle = resultBundle,
            fragments = fragments,
            titles = titles,
            activity = requireActivity()
        )

        binding.detailsViewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.detailsViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }


    private fun handleComeBack() {

        if (args.fromSearch) {
            requireActivity().finish()
        } else {
            findNavController().popBackStack()
        }
    }


}