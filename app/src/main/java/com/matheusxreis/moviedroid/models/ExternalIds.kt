package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class ExternalIds(
    @SerializedName("imdb_id")
    val imdbId:String
)