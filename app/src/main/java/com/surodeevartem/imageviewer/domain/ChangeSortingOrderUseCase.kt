package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import javax.inject.Inject

class ChangeSortingOrderUseCase @Inject constructor(
    private val sortingRepository: SortingRepository,
) {

    fun execute(order: SortingOrder) {
        sortingRepository.changeCurrentSortingOrder(order)
    }
}
