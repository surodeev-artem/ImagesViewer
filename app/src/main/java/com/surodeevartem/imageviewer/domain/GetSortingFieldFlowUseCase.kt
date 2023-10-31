package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.SortingField
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSortingFieldFlowUseCase @Inject constructor(
    private val sortingRepository: SortingRepository,
) {

    fun execute(): StateFlow<SortingField> = sortingRepository.currentSortingField
}
