package com.surodeevartem.imageviewer.data

import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.entity.SortingOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortingRepository @Inject constructor() {
    private val _currentSortingField = MutableStateFlow(SortingField.ID)
    val currentSortingField = _currentSortingField.asStateFlow()


    private val _currentSortingOrder = MutableStateFlow(SortingOrder.ASCENDING)
    val currentSortingOrder = _currentSortingOrder.asStateFlow()

    fun changeCurrentSortingField(field: SortingField) {
        _currentSortingField.value = field
    }

    fun changeCurrentSortingOrder(order: SortingOrder) {
        _currentSortingOrder.value = order
    }
}
