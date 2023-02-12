package com.matheusxreis.moviedroid.di

import android.content.Context
import android.graphics.Movie
import androidx.room.Database
import androidx.room.Room
import com.matheusxreis.moviedroid.data.database.ItemDao
import com.matheusxreis.moviedroid.data.database.MoviedroidDatabase
import com.matheusxreis.moviedroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoviedroidDatabase {
        return Room.databaseBuilder(
            context,
            MoviedroidDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(
        database: MoviedroidDatabase
    ): ItemDao {
        return database.itemDao()
    }
}