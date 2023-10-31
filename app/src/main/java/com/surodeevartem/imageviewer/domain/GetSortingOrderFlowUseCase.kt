package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSortingOrderFlowUseCase @Inject constructor(
    private val sortingRepository: SortingRepository,
) {

    fun execute(): StateFlow<SortingOrder> = sortingRepository.currentSortingOrder
}
