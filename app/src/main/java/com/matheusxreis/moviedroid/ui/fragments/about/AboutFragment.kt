package com.matheusxreis.moviedroid.ui.fragments.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var _binding:FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val castAdapter:CreditsAdapter by lazy {
        CreditsAdapter()
    }
    private val crewAdapter:CreditsAdapter by lazy {
        CreditsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding?.detailsViewModel = detailsViewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        disablingTabWhenRvScroll()
        populateData()


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // CUSTOM FUNCTIONS

    private fun populateData(){
        detailsViewModel.details.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    //overviewTv.text = it.data?.overview
                    binding?.movieDetails = it.data
                    castAdapter.setData(it.data?.credits?.cast!!)
                    crewAdapter.setData(it.data?.credits?.crew!!)
                }
                else -> {

                }
            }
        }
    }
    private fun setUpRecyclerView(){

        binding?.castRecyclerView?.adapter = castAdapter
        binding?.crewRecyclerView?.adapter = crewAdapter

        binding?.castRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.crewRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun disablingTabWhenRvScroll(){
        val touchListener = object: RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                binding?.castRecyclerView?.parent?.requestDisallowInterceptTouchEvent(true)
                binding?.crewRecyclerView?.parent?.requestDisallowInterceptTouchEvent(true)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        }
        binding?.castRecyclerView?.addOnItemTouchListener(touchListener)
        binding?.crewRecyclerView?.addOnItemTouchListener(touchListener)

    }



}