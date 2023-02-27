package com.matheusxreis.moviedroid.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.matheusxreis.moviedroid.utils.Constants

@Entity(
    tableName = Constants.TABLE_ITEMS_LIST_NAME,
    foreignKeys = [ForeignKey(
        entity = ListEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("listCode"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ListItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val itemId: String,
    val imageUrl:String,
    val voteAverage: Float,
    val voteCount: Int,
    val type: String,
    val rating:Float,
    val listCode: String,
    val firstAirDate:String?
)