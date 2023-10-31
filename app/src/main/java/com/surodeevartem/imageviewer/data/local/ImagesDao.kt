package com.surodeevartem.imageviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surodeevartem.imageviewer.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Query("""SELECT * FROM favorite_images ORDER BY
                CASE WHEN :isAscending = 1 THEN id END ASC,
                CASE WHEN :isAscending = 0 THEN id END DESC
            """)
    fun getAllSortedById(isAscending: Boolean): Flow<List<ImageEntity>>

    @Query("""SELECT * FROM favorite_images ORDER BY
                CASE WHEN :isAscending = 1 THEN title END ASC,
                CASE WHEN :isAscending = 0 THEN title END DESC
            """)
    fun getAllSortedByTitle(isAscending: Boolean): Flow<List<ImageEntity>>

    @Query("SELECT id FROM favorite_images")
    fun getAllIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Query("DELETE FROM favorite_images WHERE id = :id")
    suspend fun delete(id: Int)
}
