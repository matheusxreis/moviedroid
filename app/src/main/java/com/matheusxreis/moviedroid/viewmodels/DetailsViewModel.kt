package com.matheusxreis.moviedroid.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.models.MovieDetails
import com.matheusxreis.moviedroid.utils.Constants
import com.matheusxreis.moviedroid.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val details: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()

    fun getDetails(
        id: String,
        mediaType: String
    ) = viewModelScope.launch {
        details.value = NetworkResult.Loading()
        Log.d("funcionou", id)
        try {

            val response = repository.remoteDataSource.getDetails(
                queries = applyQueries(),
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

    private fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries["api_key"] = Constants.API_KEY
        queries["append_to_response"] = "credits"
        return queries
    }


}