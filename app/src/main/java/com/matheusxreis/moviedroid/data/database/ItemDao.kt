package com.matheusxreis.moviedroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    // movie and series
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteItem(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorites_table ORDER BY itemId")
    fun readFavoriteItem(): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorites_table WHERE itemId==:itemId")
    fun deleteFavoriteItem(itemId:String)

    //lists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listEntity: ListEntity)

    @Query("SELECT * FROM lists_table ORDER BY id")
    fun readLists():Flow<List<ListEntity>>

}