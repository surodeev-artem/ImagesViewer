package com.surodeevartem.imageviewer.data.remote

import com.surodeevartem.imageviewer.entity.ImageEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesService {

    @GET("photos")
    suspend fun pagedImages(
        @Query("_start") startIndex: Int,
        @Query("_limit") pageSize: Int,
        @Query("_sort") sortBy: String,
        @Query("_order") sortOrder: String,
    ): List<ImageEntity>
}
