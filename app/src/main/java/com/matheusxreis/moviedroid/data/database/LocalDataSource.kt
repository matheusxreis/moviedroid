package com.matheusxreis.moviedroid.data.database

import com.matheusxreis.moviedroid.data.database.entities.FavoriteEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val itemDao: ItemDao
){

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) = itemDao.insertFavoriteItem(favoriteEntity)
    fun readFavorite() = itemDao.readFavoriteItem()
    fun deleteFavorite(itemId:String) = itemDao.deleteFavoriteItem(itemId)
}