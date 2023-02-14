package com.matheusxreis.moviedroid.di

import com.google.gson.GsonBuilder
import com.matheusxreis.moviedroid.data.network.MovieAPI
import com.matheusxreis.moviedroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideGsonConverterFactory():GsonConverterFactory {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return GsonConverterFactory
            .create(gson)
    }

    @Provides
    @Singleton
    fun provideHttpClient():OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ):Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieApi(
        retrofit: Retrofit
    ):MovieAPI {
       return retrofit.create(MovieAPI::class.java)
    }
}