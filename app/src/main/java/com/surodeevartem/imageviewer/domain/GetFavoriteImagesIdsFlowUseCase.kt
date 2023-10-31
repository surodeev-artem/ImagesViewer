package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesIdsFlowUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {

    fun execute(): Flow<List<Int>> {
        return favoritesRepository.getAllFavoritesId()
    }
}
