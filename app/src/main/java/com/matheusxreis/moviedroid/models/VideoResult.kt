package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("id")
    val id:String,
    @SerializedName("results")
    val videos: List<Video>
)