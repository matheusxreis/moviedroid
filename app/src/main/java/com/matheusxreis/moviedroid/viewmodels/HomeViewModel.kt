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
): ViewModel() {


    val trendingSeries: MutableLiveData<List<MoviePoster>> = MutableLiveData()

    fun getTrendingSeries() = viewModelScope.launch {
        val response = repository.remoteDataSource.getTrendingMovies(
            queries = applyQueries(),
            mediaType = "tv",
            period = "day"
        )
            Log.d("RESPONSE API", response.body().toString())
        trendingSeries.value = response.body()?.results
    }


    fun applyQueries():HashMap<String, String>{
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        return queries
    }


}