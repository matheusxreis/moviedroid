package com.matheusxreis.moviedroid.data.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
   private val movieAPI: MovieAPI
) {

    fun getTrendingMovies(queries:Map<String, String>) = movieAPI.getTrendingDay(queries)

}