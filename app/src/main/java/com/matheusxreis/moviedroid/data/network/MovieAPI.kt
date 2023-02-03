package com.matheusxreis.moviedroid.data.network

import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.models.ResultApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieAPI {

    @GET("trending/{media_type}/{period}")
    suspend fun getTrendingDay(
        @Path("media_type") mediaType: String,
        @Path("period") period: String,
        @QueryMap queries: Map<String, String>,
    ): Response<ResultApi>


    @GET("movie/{filter}")
    suspend fun getMovies(
        @Path("filter") filter: String,
        @QueryMap queries: Map<String, String>
    ): Response<ResultApi>

    @GET("tv/{filter}")
    suspend fun getTv(
        @Path("filter") filter: String,
        @QueryMap queries: Map<String, String>
    ): Response<ResultApi>
}
