package com.matheusxreis.moviedroid.data.database

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val itemDao: ItemDao
){

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) = itemDao.insertFavoriteItem(favoriteEntity)
    suspend fun readFavorite() = itemDao.readFavoriteItem()

}