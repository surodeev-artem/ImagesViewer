package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.FavoritesRepository
import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val sortingRepository: SortingRepository,
) {

    fun execute(): Flow<ImmutableList<ImageEntity>> {
        val sortingOrder = sortingRepository.currentSortingOrder.value
        val sortingField = sortingRepository.currentSortingField.value

        return favoritesRepository.getAllFavorites(
            sortingOrder = sortingOrder,
            sortingField = sortingField,
        ).map { it.toImmutableList() }
    }
}
