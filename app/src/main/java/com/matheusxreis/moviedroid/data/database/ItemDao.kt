package com.matheusxreis.moviedroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteItem(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id")
    fun readFavoriteItem(): Flow<List<FavoriteEntity>>
}