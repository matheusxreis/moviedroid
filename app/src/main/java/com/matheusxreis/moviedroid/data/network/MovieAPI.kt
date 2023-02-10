package com.matheusxreis.moviedroid.data.network

import com.matheusxreis.moviedroid.models.MovieDetails
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.models.ResultApi
import com.matheusxreis.moviedroid.models.VideoResult
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

    @GET("search/multi")
    suspend fun searchMulti(
        @QueryMap queries: Map<String, String>
    ):Response<ResultApi>

    @GET("{mediaType}/{movieId}")
    suspend fun getDetails(
        @Path("mediaType") mediaType: String,
        @Path("movieId") id:String,
        @QueryMap queries: Map<String, String>
    ):Response<MovieDetails>

    @GET("{mediaType}/{movieId}/recommendations")
    suspend fun getRecommendations(
        @Path("mediaType") mediaType: String,
        @Path("movieId") id: String,
        @QueryMap queries: Map<String, String>
    ):Response<ResultApi>

    @GET("{mediaType}/{movieId}/videos")
    suspend fun getVideos(
        @Path("mediaType") mediaType: String,
        @Path("movieId") id: String,
        @QueryMap queries: Map<String, String>
    ):Response<VideoResult>
}

