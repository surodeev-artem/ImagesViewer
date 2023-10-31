package com.surodeevartem.imageviewer.presentation.images.all

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.surodeevartem.imageviewer.entity.SortingOrder
import com.surodeevartem.imageviewer.domain.GetImagesPagingDataUseCase
import com.surodeevartem.imageviewer.domain.GetSortingFieldFlowUseCase
import com.surodeevartem.imageviewer.domain.GetSortingOrderFlowUseCase
import com.surodeevartem.imageviewer.entity.SortingField
import com.surodeevartem.imageviewer.utils.NetworkConnectionObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllImagesViewModel @Inject constructor(
    private val getImagesPagingDataUseCase: GetImagesPagingDataUseCase,
    getSortingFieldFlowUseCase: GetSortingFieldFlowUseCase,
    getSortingOrderFlowUseCase: GetSortingOrderFlowUseCase,
    networkConnectionObserver: NetworkConnectionObserver,
) : ViewModel() {

    private val sortingOrder = getSortingOrderFlowUseCase.execute()
    private val sortingField = getSortingFieldFlowUseCase.execute()

    var images by mutableStateOf(getImagesFlow())
        private set
    val hasConnection = networkConnectionObserver.hasConnection

    init {
        viewModelScope.launch {
            sortingOrder.collect {
                images = getImagesFlow()
            }
        }

        viewModelScope.launch {
            sortingField.collect {
                images = getImagesFlow()
            }
        }
    }

    private fun getImagesFlow() = getImagesPagingDataUseCase
        .execute()
        .cachedIn(viewModelScope)
}
