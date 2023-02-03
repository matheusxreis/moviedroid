package com.matheusxreis.moviedroid.utils

import com.matheusxreis.moviedroid.BuildConfig

class Constants {

    companion object {

        const val BASE_URL="https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL="https://image.tmdb.org/t/p/original"
        val API_KEY= BuildConfig.apiKey

    }

}