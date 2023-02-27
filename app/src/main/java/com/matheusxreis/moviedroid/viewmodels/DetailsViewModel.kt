package com.matheusxreis.moviedroid.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import com.matheusxreis.moviedroid.models.MovieDetails
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.models.Video
import com.matheusxreis.moviedroid.utils.Constants
import com.matheusxreis.moviedroid.utils.NetworkListener
import com.matheusxreis.moviedroid.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    application:Application
    ) : AndroidViewModel(application) {


    val details: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()
    val recommendations: MutableLiveData<NetworkResult<List<MoviePoster>>> = MutableLiveData()

    val videos: MutableLiveData<NetworkResult<List<Video>>> = MutableLiveData()

    val readFavorites = repository.localDataSource.readFavorite().asLiveData()

    val networkListener = NetworkListener.getInstance(application.applicationContext)


    fun getDetails(
        id: String,
        mediaType: String
    ) = viewModelScope.launch {
        details.value = NetworkResult.Loading()
        Log.d("funcionou", id)
        try {

            val response = repository.remoteDataSource.getDetails(
                queries = applyDetailsQueries(),
                id = id,
                mediaType = mediaType
            )
            Log.d("funcionou", response.toString())

            if (response.body() == null) {
                details.value = NetworkResult.Error("empty")
                return@launch
            }
            details.value = NetworkResult.Success(response.body()!!)
        } catch (e: Exception) {
            details.value = NetworkResult.Error(e.toString())
        }
    }
    fun getRecommendations(
        id:String,
        mediaType: String
    ) = viewModelScope.launch {
        recommendations.value = NetworkResult.Loading()
        try{
            val response = repository.remoteDataSource.getRecommendations(
                queries = applyQueries(),
                id = id,
                mediaType = mediaType
            )
            if(response.body()?.results.isNullOrEmpty()){
                recommendations.value = NetworkResult.Error("empty")
                return@launch
            }
            recommendations.value = NetworkResult.Success(response.body()?.results!!)
        }catch(e:Exception) {
            recommendations.value = NetworkResult.Error(e.toString())
        }

    }

    fun getVideos(
        id:String,
        mediaType: String
    ) = viewModelScope.launch{

        videos.value = NetworkResult.Loading()

        try {
            val response = repository.remoteDataSource.getVideos(
                queries = applyQueries(),
                id = id,
                mediaType = mediaType
            )
            if(response.body()?.videos.isNullOrEmpty()){
                videos.value = NetworkResult.Error("empty")
                return@launch
            }
            videos.value = NetworkResult.Success(response.body()?.videos!!)
        }catch (e:Exception){
            videos.value = NetworkResult.Error(e.toString())
        }
    }

    fun saveInFavorites(movieDetails: MovieDetails, type:String, firstAirDate:String?) = viewModelScope.launch {

        val favorite = FavoriteEntity(
            title = movieDetails.title,
            voteAverage =  movieDetails.voteAverage,
            voteCount = movieDetails.vouteCount.toInt(),
            imageUrl = movieDetails.imageUrl.toString(),
            itemId = movieDetails.id.toString(),
            rating = movieDetails.voteAverage,
            type=type,
            firstAirDate = firstAirDate
        )
        repository.localDataSource.insertFavorite(favorite)
    }
    fun deleteFromFavorite(itemId:String)= viewModelScope.launch(Dispatchers.IO) {
        repository.localDataSource.deleteFavorite(itemId)
    }


    private fun applyDetailsQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        queries["append_to_response"] = "credits"
        return queries
    }
    private fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        return queries
    }


}