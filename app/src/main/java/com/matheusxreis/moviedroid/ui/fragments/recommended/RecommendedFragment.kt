package com.matheusxreis.moviedroid.ui.fragments.recommended

import MoviesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.databinding.FragmentRecommendedBinding
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel

class RecommendedFragment : Fragment() {

    private var _binding: FragmentRecommendedBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by activityViewModels<DetailsViewModel>()
    private val movieAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            // param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_recommended, container, false)

        _binding = FragmentRecommendedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setUpRecyclerView()
        populateRecyclerView()

        return binding.root
    }

    override fun onDestroy() {

        _binding = null
        super.onDestroy()
    }

    // CUSTOM FUNCTIONS

    private fun setUpRecyclerView() {

        binding.recommendRecyclerView.adapter = movieAdapter
        binding.recommendRecyclerView.layoutManager =
           GridLayoutManager(requireContext(), 2)

    }

    private fun populateRecyclerView(){
        detailsViewModel.recommendations.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    movieAdapter.setData(it.data!!)
                }
                else -> {

                }
            }
        }
    }
}