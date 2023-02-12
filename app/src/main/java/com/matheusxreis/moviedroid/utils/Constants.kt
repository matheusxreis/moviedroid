package com.matheusxreis.moviedroid.utils

import com.matheusxreis.moviedroid.BuildConfig

class Constants {

    companion object {

        const val BASE_URL="https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL="https://image.tmdb.org/t/p/original"
        const val YOUTUBE_BASE_URL="https://www.youtube.com/watch?v="
        val API_KEY= BuildConfig.apiKey

        // ROOM

        const val DATABASE_NAME="moviedroid_db"
        const val TABLE_FAVORITES_NAME="favorites_table"
        const val TABLE_LISTS_NAME="lists_table"

    }

}