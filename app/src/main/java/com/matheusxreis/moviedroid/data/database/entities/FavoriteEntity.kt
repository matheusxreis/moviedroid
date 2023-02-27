package com.matheusxreis.moviedroid.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.matheusxreis.moviedroid.utils.Constants


@Entity(tableName=Constants.TABLE_FAVORITES_NAME)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val itemId: String,
    val imageUrl:String,
    val type: String,
    val rating:Float,
    val voteAverage: Float,
    val voteCount: Int,
    val listCode: String = "favorites"
)