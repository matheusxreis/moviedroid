package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class ResultApi(
    @SerializedName("page")
    val page: Int,

    @SerializedName("result")
    val results: List<MoviePoster>
)