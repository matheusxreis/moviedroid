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

    suspend fun getDetails(queries: Map<String, String>, id: String, mediaType: String) = movieAPI.getDetails(
        queries = queries, id = id, mediaType = mediaType
    )

    suspend fun getRecommendations(queries: Map<String, String>, id: String, mediaType: String) = movieAPI.getRecommendations(
        queries = queries, id = id, mediaType =  mediaType
    )

    suspend fun getVideos(queries: Map<String, String>, id: String, mediaType: String) = movieAPI.getVideos(
        queries = queries, id = id, mediaType = mediaType
    )
}