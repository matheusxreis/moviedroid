package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("id")
    private val id:String,
    @SerializedName("results")
    private val videos: List<Video>
)