package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.FavoritesRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun execute(): List<ImageEntity> {
        return favoritesRepository.getAllFavorites()
    }
}
