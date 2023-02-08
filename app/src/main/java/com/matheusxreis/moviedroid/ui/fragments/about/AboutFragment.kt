package com.matheusxreis.moviedroid.ui.fragments.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.databinding.FragmentAboutBinding
import com.matheusxreis.moviedroid.databinding.FragmentDetailsBinding
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by activityViewModels<DetailsViewModel>()
    private lateinit var binding:FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // inflater.inflate(R.layout.fragment_about, container, false)

        binding = FragmentAboutBinding.inflate(inflater)

        val movie: MoviePoster? = arguments?.getParcelable("movie")
        if(movie != null){
            binding.movie = movie
        }
        binding.detailsViewModel = detailsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       detailsViewModel.details.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                   //overviewTv.text = it.data?.overview
                    binding.movieDetails = it.data
                }
                else -> {

               }
            }
        }
    }

}