package com.matheusxreis.moviedroid.data.database

import androidx.room.*
import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
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

    @Query("DELETE FROM lists_table WHERE id==:id")
    fun deleteList(id:String)

    @Query("DELETE FROM lists_table WHERE name!='Favorites'")
    fun deleteAllLists()

    @Update
    fun updateNameList(listEntity: ListEntity)

    @Update
    fun updateAmountAndCover(listEntity: ListEntity)

    //list item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(listItemEntity: ListItemEntity)

    @Query("SELECT * FROM items_List_table WHERE listCode == :listCode ORDER BY id")
    fun readListItem(listCode:String):Flow<List<ListItemEntity>>

    @Query("DELETE FROM items_List_table WHERE id==:id")
    fun deleteListItem(id: String)

    @Query("DELETE FROM items_List_table WHERE listCode==:listCode")
    fun deleteAllListItemsFromList(listCode: String)

}