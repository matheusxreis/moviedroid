package com.matheusxreis.moviedroid.data.network

import okhttp3.MediaType
import java.time.chrono.ChronoPeriod
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
   private val movieAPI: MovieAPI
) {

    suspend fun getTrendingMovies(queries:Map<String, String>, mediaType: String, period: String) = movieAPI.getTrendingDay(queries, mediaType, period)

}