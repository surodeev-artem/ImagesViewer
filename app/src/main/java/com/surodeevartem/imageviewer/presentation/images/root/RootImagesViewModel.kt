package com.surodeevartem.imageviewer.presentation.images.root

import androidx.lifecycle.ViewModel
import com.surodeevartem.imageviewer.domain.ChangeSortingFieldUseCase
import com.surodeevartem.imageviewer.domain.ChangeSortingOrderUseCase
import com.surodeevartem.imageviewer.domain.GetSortingFieldFlowUseCase
import com.surodeevartem.imageviewer.domain.GetSortingOrderFlowUseCase
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootImagesViewModel @Inject constructor(
    private val changeSortingFieldUseCase: ChangeSortingFieldUseCase,
    private val changeSortingOrderUseCase: ChangeSortingOrderUseCase,
    getSortingFieldFlowUseCase: GetSortingFieldFlowUseCase,
    getSortingOrderFlowUseCase: GetSortingOrderFlowUseCase,
) : ViewModel() {
    val sortingField = getSortingFieldFlowUseCase.execute()
    val sortingOrder = getSortingOrderFlowUseCase.execute()

    fun changeSortingOrder(order: SortingOrder) {
        changeSortingOrderUseCase.execute(order)
    }

    fun changeSortingField(field: SortingField) {
        changeSortingFieldUseCase.execute(field)
    }
}
