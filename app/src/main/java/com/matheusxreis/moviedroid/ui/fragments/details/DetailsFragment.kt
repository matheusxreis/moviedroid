package com.matheusxreis.moviedroid.ui.fragments.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.DetailsPagerAdapter
import com.matheusxreis.moviedroid.databinding.FragmentDetailsBinding
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.ui.fragments.about.AboutFragment
import com.matheusxreis.moviedroid.ui.fragments.recommended.RecommendedFragment
import com.matheusxreis.moviedroid.utils.NetworkResult
import com.matheusxreis.moviedroid.viewmodels.DetailsViewModel
import com.matheusxreis.moviedroid.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), MenuProvider {

    val args by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by activityViewModels<DetailsViewModel>()
    private var isOnFavorites = false


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
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.detailsViewModel = detailsViewModel
        binding.movie = args.movie

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setUpAndPopulateViewPager()


        fetchDetails(args.movie)
        fetchRecommendations(args.movie)

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    // MENU PROVIDER

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_details_fragment, menu)

        menu.forEach {
            if (it.itemId == R.id.favorite_menu) {
                setColorIfOnFavorites(args.movie.id, it)
            } else {
                setTintIconMenuItem(it)
            }
        }

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                handleComeBack()
            }
            R.id.videos_menu -> {
                var mediaType = "tv"
                args.movie.let {
                    if (it.firstAirDate.isNullOrEmpty()) {
                        mediaType = "movie"
                    }
                }
                val action = DetailsFragmentDirections.actionDetailsFragmentToVideosFragment(
                    movieId = args.movie.id,
                    mediaType = mediaType
                )
                findNavController().navigate(action)
            }
            R.id.favorite_menu -> {

                if (!isOnFavorites) {
                    saveInFavorites()
                    setTintIconMenuItem(menuItem, R.color.purple_200)
                }else {
                    deleteFromFavorites()
                    setTintIconMenuItem(menuItem, R.color.white)

                }
            }
        }
        return true
    }


    // CUSTOM FUNCTIONS

    private fun setColorIfOnFavorites(id: String, menuItem: MenuItem) {
        detailsViewModel.readFavorites.observe(viewLifecycleOwner) { favoriteList ->
            val isOnFavorite = favoriteList.find { favorite -> favorite.itemId == id }

            if (isOnFavorite != null) {
                setTintIconMenuItem(menuItem, R.color.purple_200)
                isOnFavorites = true
            } else {
                setTintIconMenuItem(menuItem)
                isOnFavorites = false
            }
        }
    }

    private fun saveInFavorites(undo:Boolean = false){
        var mediaType = "tv"

        val movieDetails = detailsViewModel.details.value?.data
        args.movie.let {
            if (it.firstAirDate.isNullOrEmpty()) {
                mediaType = "movie"
            }
        }

        detailsViewModel.saveInFavorites(
            movieDetails = movieDetails!!,
            type = mediaType
        )

        if(!undo){
            Snackbar.make(binding.root, "Save in favorites", Snackbar.LENGTH_SHORT)
                .setAction("Undo", {deleteFromFavorites(true)})
                //  .setAction("Okay", {})
                .show()
        }

    }

    private fun deleteFromFavorites(undo: Boolean = false){
        val itemId = detailsViewModel.details.value?.data?.id
        if(itemId != null){
            detailsViewModel.deleteFromFavorite(itemId = itemId.toString())

            if(!undo){
                Snackbar.make(binding.root, "Delete from favorites", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", {saveInFavorites(true)})
                    // .setAction("Okay", {})
                    .show()
            }
        }


    }

    private fun setUpAndPopulateViewPager() {

        val fragments = ArrayList<Fragment>()
        fragments.add(AboutFragment())
        fragments.add(RecommendedFragment())

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

    private fun setTintIconMenuItem(menuItem: MenuItem, color: Int = R.color.white) {
        menuItem.icon?.setTint(ContextCompat.getColor(requireActivity(), color))
    }

    private fun handleComeBack() {

        if (args.fromSearch) {
            requireActivity().finish()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun fetchDetails(moviePoster: MoviePoster) {
        var mediaType = "tv"
        moviePoster.let {
            if (it.firstAirDate.isNullOrEmpty()) {
                mediaType = "movie"
            }
        }
        detailsViewModel.getDetails(id = moviePoster.id, mediaType = mediaType)
        detailsViewModel.details.observe(viewLifecycleOwner) {
            if (it is NetworkResult.Success) {
                binding.movieDetails = it.data
            }
        }
    }

    private fun fetchRecommendations(moviePoster: MoviePoster) {
        var mediaType = "tv"
        moviePoster.let {
            if (it.firstAirDate.isNullOrEmpty()) {
                mediaType = "movie"
            }
        }
        detailsViewModel.getRecommendations(
            id = moviePoster.id,
            mediaType = mediaType
        )
    }


}