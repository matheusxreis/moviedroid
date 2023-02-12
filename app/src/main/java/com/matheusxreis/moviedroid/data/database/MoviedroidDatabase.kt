package com.matheusxreis.moviedroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MoviedroidDatabase:RoomDatabase() {

    abstract fun itemDao(): ItemDao
}