package com.surodeevartem.imageviewer.domain

import androidx.paging.PagingData
import com.surodeevartem.imageviewer.data.ImagesRepository
import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesPagingDataUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val sortingRepository: SortingRepository,
) {

    fun execute(): Flow<PagingData<ImageEntity>> {
        val sortingOrder = sortingRepository.currentSortingOrder.value
        val sortingField = sortingRepository.currentSortingField.value

        return imagesRepository.getNewImagesPagingData(
            sortingOrder = sortingOrder,
            sortingField = sortingField,
        )
    }
}
