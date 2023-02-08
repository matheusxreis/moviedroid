package com.matheusxreis.moviedroid.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.utils.Constants
import com.matheusxreis.moviedroid.utils.NetworkListener
import com.matheusxreis.moviedroid.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    val trendingSeries: MutableLiveData<NetworkResult<List<MoviePoster>>> = MutableLiveData()
    val popularMovies: MutableLiveData<NetworkResult<List<MoviePoster>>> = MutableLiveData()
    val popularSeries: MutableLiveData<NetworkResult<List<MoviePoster>>> = MutableLiveData()
    val searchedResult: MutableLiveData<NetworkResult<List<MoviePoster>>> = MutableLiveData()

    val networkListener = NetworkListener()

    var hasDesconnected = false

    private var pageMovie = 1
    private val pageSerie = 1

    val context = application.applicationContext

    fun getTrendingSeries() = viewModelScope.launch {

        trendingSeries.value = NetworkResult.Loading()

        try {
            val response = repository.remoteDataSource.getTrendingMovies(
                queries = applyQueries(),
                mediaType = "tv",
                period = "day"
            )
            if (response?.body()?.results.isNullOrEmpty()) {
                trendingSeries.value = NetworkResult.Error("empty")
            } else {
                trendingSeries.value = NetworkResult.Success(response.body()?.results!!)
            }
        } catch (e: Exception) {

            trendingSeries.value = NetworkResult.Error(e.toString())
        }
    }

    fun getMovies(filter: String) = viewModelScope.launch {

        try {

            trendingSeries.value = NetworkResult.Loading()

            val response = repository.remoteDataSource.getMovies(
                queries = applyMoviesQueries(),
                filter = filter
            )
            if (response?.body()?.results.isNullOrEmpty()) {
                popularMovies.value = NetworkResult.Error("empty")
                return@launch
            }
            when (filter) {
                "popular" -> {
                    popularMovies.value = NetworkResult.Success(response.body()?.results!!)
                }
            }
        } catch (e: Exception) {

            popularMovies.value = NetworkResult.Error(e.toString())

        }

    }

    fun getTv(filter: String) = viewModelScope.launch {

        popularSeries.value = NetworkResult.Loading()
        try {
            val response = repository.remoteDataSource.getTv(
                queries = applySeriesQueries(),
                filter = filter
            )

            if (response?.body()?.results.isNullOrEmpty()) {
                popularSeries.value = NetworkResult.Error("empty")
                return@launch
            }
            when (filter) {
                "popular" -> {
                    Log.d("RESPONSE API", response.toString())
                    popularSeries.value = NetworkResult.Success(response.body()?.results!!)
                }
            }
        } catch (e: Exception) {
            popularSeries.value = NetworkResult.Error(e.toString())
        }

    }

    fun search(searchQuery: String) = viewModelScope.launch {

        searchedResult.value = NetworkResult.Loading()

        try {
            val response = repository.remoteDataSource.searchMulti(
                queries = applySearchQueries(searchQuery)
            )

            if (response?.body()?.results.isNullOrEmpty()) {
                searchedResult.value = NetworkResult.Error("empty")
            }

            if (!response.body()?.results.isNullOrEmpty()) {
                searchedResult.value = NetworkResult.Success(response.body()?.results!!)
            }
        } catch (e: Exception) {

            searchedResult.value = NetworkResult.Error(e.toString())

        }
    }

//    fun getNewPageMovies(filter: String) = viewModelScope.launch {
//        val response = repository.remoteDataSource.getMovies(
//            queries = applyMoviesQueries(pageMovie + 1),
//            filter = filter
//        )
//        pageMovie = pageMovie + 1
//        when (filter) {
//            "popular" -> {
//
//                val newList: ArrayList<MoviePoster> = arrayListOf()
//                popularMovies.value?.let { newList.addAll(it) }
//                response.body()?.results.let { newList.addAll(it!!) }
//                Log.d("respeeonse", response.body().toString())
//                popularMovies.value = newList
//            }
//        }
//
//    }


    private fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        return queries
    }

    private fun applyMoviesQueries(page: Int = 1): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        queries["language"] = "en-US"
        queries["page"] = page.toString()
        return queries
    }

    private fun applySeriesQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        queries["language"] = "en-US"
        queries["page"] = pageSerie.toString()
        return queries
    }

    private fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        queries["language"] = "en-US"
        queries["page"] = "1"
        queries["include_adult"] = "true"
        queries["query"] = searchQuery

        return queries
    }




}