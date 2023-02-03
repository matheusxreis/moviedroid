package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class MoviePoster(
    @SerializedName("poster_path")
    val imageUrl: String,
    @SerializedName("id")
    val imdbId: String,
    @SerializedName("original_title")
    val title: String
)