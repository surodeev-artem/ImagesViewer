package com.surodeevartem.imageviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surodeevartem.imageviewer.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Query("SELECT * FROM favorite_images")
    suspend fun getAll(): List<ImageEntity>

    @Query("SELECT id FROM favorite_images")
    fun getAllIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Query("DELETE FROM favorite_images WHERE id = :id")
    suspend fun delete(id: Int)
}
