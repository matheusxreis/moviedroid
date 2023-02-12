package com.matheusxreis.moviedroid.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.moviedroid.utils.Constants


@Entity(tableName=Constants.TABLE_FAVORITES_NAME)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val itemId: String,
    val imageUrl:String,
    val overview: String,
    val type: String,
    val rating:Float,

    val listCode: String = "favorites"
)