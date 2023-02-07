package com.matheusxreis.moviedroid.viewmodels

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
        id: String
    ) = viewModelScope.launch {
        details.value = NetworkResult.Loading()
        try {

            val response = repository.remoteDataSource.getDetails(
                queries = applyQueries(),
                id = id
            )

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
        return queries
    }


}