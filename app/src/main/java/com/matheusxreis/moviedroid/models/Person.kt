package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("known_for_department")
    val knownFor:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("profile_path")
    val image:String,
    @SerializedName("character")
    val character:String?
)
