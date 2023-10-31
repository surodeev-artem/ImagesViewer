package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.FavoritesRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.utils.Result
import javax.inject.Inject

class RemoveFavoriteImageUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun execute(id: Int): Result<Unit> {
        return favoritesRepository.unsetLike(id)
    }
}
