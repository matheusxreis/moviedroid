package com.matheusxreis.moviedroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity

@Database(
    entities = [FavoriteEntity::class, ListEntity::class, ListItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MoviedroidDatabase:RoomDatabase() {

    abstract fun itemDao(): ItemDao
}