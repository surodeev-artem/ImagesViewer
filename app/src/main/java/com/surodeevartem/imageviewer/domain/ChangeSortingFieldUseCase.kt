package com.surodeevartem.imageviewer.domain

import com.surodeevartem.imageviewer.data.SortingRepository
import com.surodeevartem.imageviewer.entity.SortingField
import javax.inject.Inject

class ChangeSortingFieldUseCase @Inject constructor(
    private val sortingRepository: SortingRepository,
) {

    fun execute(field: SortingField) {
        sortingRepository.changeCurrentSortingField(field)
    }
}
