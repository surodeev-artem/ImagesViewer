package com.surodeevartem.imageviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surodeevartem.imageviewer.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}
