package com.surodeevartem.imageviewer.data

import com.surodeevartem.imageviewer.data.local.ImagesDatabase
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val imagesDatabase: ImagesDatabase,
) {

    fun getAllFavorites(
        sortingOrder: SortingOrder,
        sortingField: SortingField,
    ): Flow<List<ImageEntity>> {
        return when (sortingField) {
            SortingField.TITLE -> imagesDatabase.imagesDao().getAllSortedByTitle(
                isAscending = sortingOrder == SortingOrder.ASCENDING,
            )
            SortingField.ID -> imagesDatabase.imagesDao().getAllSortedById(
                isAscending = sortingOrder == SortingOrder.ASCENDING,
            )
        }
    }

    suspend fun getAllFavoritesId(): Flow<List<Int>> {
        return imagesDatabase.imagesDao().getAllIds()
    }

    suspend fun setLike(image: ImageEntity): Result<Unit> {
        return try {
            imagesDatabase.imagesDao().insert(image)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    suspend fun unsetLike(id: Int): Result<Unit> {
        return try {
            imagesDatabase.imagesDao().delete(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
