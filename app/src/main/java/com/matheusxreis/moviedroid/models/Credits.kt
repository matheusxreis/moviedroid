package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    val cast: List<Cast>
)
