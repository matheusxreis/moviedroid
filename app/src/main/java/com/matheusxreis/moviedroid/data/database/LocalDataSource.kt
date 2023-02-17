package com.matheusxreis.moviedroid.data.database

import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val itemDao: ItemDao
){

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) = itemDao.insertFavoriteItem(favoriteEntity)
    fun readFavorite() = itemDao.readFavoriteItem()
    fun deleteFavorite(itemId:String) = itemDao.deleteFavoriteItem(itemId)


    suspend fun insertList(listEntity: ListEntity) = itemDao.insertList(listEntity)
    fun readLists() = itemDao.readLists()
    fun deleteList(id:String) = itemDao.deleteList(id)
    fun deleteAllLists() = itemDao.deleteAllLists()
    fun updateNameList(listEntity: ListEntity) = itemDao.updateNameList(listEntity)
    fun updateAmmountAndCover(listEntity: ListEntity) = itemDao.updateAmountAndCover(listEntity)


    suspend fun insertItem(listItemEntity: ListItemEntity) = itemDao.insertItem(listItemEntity)
}