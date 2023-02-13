package com.matheusxreis.moviedroid.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matheusxreis.moviedroid.utils.Constants
import java.util.Date

@Entity(tableName = Constants.TABLE_LISTS_NAME)
data class ListEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String = "",
    val createdAt: Long = Date().time,
    val coverUrl: String
    )