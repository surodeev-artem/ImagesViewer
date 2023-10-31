package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.FavoritesRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.utils.Result
import javax.inject.Inject

class AddFavoriteImageUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun execute(image: ImageEntity): Result<Unit> {
        return favoritesRepository.setLike(image)
    }
}
