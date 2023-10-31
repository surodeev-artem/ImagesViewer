package com.surodeevartem.imageviewer.entity

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "favorite_images")
data class ImageEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val url: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
)
