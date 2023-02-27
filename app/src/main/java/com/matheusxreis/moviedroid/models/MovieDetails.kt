package com.matheusxreis.moviedroid.models

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("backdrop_path")
    val backdropImageUrl: String,
    @SerializedName("poster_path")
    val imageUrl: String?,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("status")
    val status:String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val vouteCount: Number,
    @SerializedName("release_date", alternate = ["first_air_date"])
    val realeasedAt: String,
    @SerializedName("id")
    val id: Number,
    @SerializedName("original_title", alternate = ["original_name", "name", "title"])
    val title: String,
    @SerializedName("credits")
    val credits: Credits,
    @SerializedName("number_of_seasons")
    val seasons: Int,
    @SerializedName("number_of_episodes")
    val amountEpisodes: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("external_ids")
    val externalIds: ExternalIds

)