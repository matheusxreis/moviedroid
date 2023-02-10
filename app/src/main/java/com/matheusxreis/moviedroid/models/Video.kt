package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("key")
    val key: String,
    @SerializedName("site")
    val from: String,
    @SerializedName("name")
    val title: String
)
