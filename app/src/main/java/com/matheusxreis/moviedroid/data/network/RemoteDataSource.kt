package com.matheusxreis.moviedroid.data.network

import okhttp3.MediaType
import java.time.chrono.ChronoPeriod
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieAPI: MovieAPI
) {

    suspend fun getTrendingMovies(queries: Map<String, String>, mediaType: String, period: String) =
        movieAPI.getTrendingDay(
            queries = queries, mediaType = mediaType, period = period
        )

    suspend fun getMovies(queries: Map<String, String>, filter: String) = movieAPI.getMovies(
        queries = queries,
        filter = filter
    )

    suspend fun getTv(queries: Map<String, String>, filter: String) = movieAPI.getTv(
        queries = queries,
        filter = filter
    )

    suspend fun searchMulti(queries: Map<String, String>) = movieAPI.searchMulti(queries)

}