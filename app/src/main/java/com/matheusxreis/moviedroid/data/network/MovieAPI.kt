package com.matheusxreis.moviedroid.data.network

import com.matheusxreis.moviedroid.models.MoviePoster
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieAPI {

    @GET("trending")
    fun getTrendingDay(
        @QueryMap queries: Map<String, String>
    ): Response<MoviePoster>
}