package com.surodeevartem.imageviewer.domain

import android.util.Log
import com.surodeevartem.imageviewer.data.FavoritesRepository
import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val sortingRepository: SortingRepository,
) {

    fun execute(): Flow<List<ImageEntity>> {
        val sortingOrder = sortingRepository.currentSortingOrder.value
        val sortingField = sortingRepository.currentSortingField.value

        Log.d("AAA", "$sortingField")
        Log.d("AAA", "$sortingOrder")

        return favoritesRepository.getAllFavorites(
            sortingOrder = sortingOrder,
            sortingField = sortingField,
        ).also { Log.d("AAA", "$it") }
    }
}
