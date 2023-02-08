package com.matheusxreis.moviedroid.ui.fragments.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.CreditsAdapter
import com.matheusxreis.moviedroid.databinding.FragmentAboutBinding
import com.matheusxreis.moviedroid.databinding.FragmentDetailsBinding
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.movies_row_layout.view.*

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by activityViewModels<DetailsViewModel>()
    private lateinit var binding:FragmentAboutBinding
    private val creditsAdapter:CreditsAdapter by lazy {
        CreditsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAboutBinding.inflate(inflater)

        binding.detailsViewModel = detailsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        populateData()

    }


    // CUSTOM FUNCTIONS

    private fun populateData(){
        detailsViewModel.details.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    //overviewTv.text = it.data?.overview
                    binding.movieDetails = it.data
                    creditsAdapter.setData(it.data?.credits?.cast!!)
                }
                else -> {

                }
            }
        }
    }
    private fun setUpRecyclerView(){
        binding.castRecyclerView.adapter = creditsAdapter
        binding.castRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }



}