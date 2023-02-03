package com.matheusxreis.moviedroid.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val trendingSeries: MutableLiveData<List<MoviePoster>> = MutableLiveData()
    val popularMovies: MutableLiveData<List<MoviePoster>> = MutableLiveData()

    fun getTrendingSeries() = viewModelScope.launch {

        val response = repository.remoteDataSource.getTrendingMovies(
            queries = applyQueries(),
            mediaType = "tv",
            period = "day"
        )
        trendingSeries.value = response.body()?.results
    }

    fun getMovies(filter: String) = viewModelScope.launch {

        val response = repository.remoteDataSource.getMovies(
            queries = applyQueries(),
            filter = filter
        )
        when(filter){
            "popular" -> {
                Log.d("RESPONSE API", response.toString())
                popularMovies.value = response.body()?.results
            }
        }

    }


    fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        return queries
    }


}