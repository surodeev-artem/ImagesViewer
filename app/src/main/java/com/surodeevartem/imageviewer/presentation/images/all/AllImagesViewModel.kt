package com.surodeevartem.imageviewer.presentation.images.all

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.surodeevartem.imageviewer.domain.AddFavoriteImageUseCase
import com.surodeevartem.imageviewer.domain.GetFavoriteImagesIdsFlowUseCase
import com.surodeevartem.imageviewer.domain.GetImagesPagingDataUseCase
import com.surodeevartem.imageviewer.domain.GetSortingFieldFlowUseCase
import com.surodeevartem.imageviewer.domain.GetSortingOrderFlowUseCase
import com.surodeevartem.imageviewer.domain.RemoveFavoriteImageUseCase
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.utils.NetworkConnectionObserver
import com.surodeevartem.imageviewer.utils.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllImagesViewModel @Inject constructor(
    private val getImagesPagingDataUseCase: GetImagesPagingDataUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
    getFavoriteImagesIdsFlowUseCase: GetFavoriteImagesIdsFlowUseCase,
    getSortingFieldFlowUseCase: GetSortingFieldFlowUseCase,
    getSortingOrderFlowUseCase: GetSortingOrderFlowUseCase,
    networkConnectionObserver: NetworkConnectionObserver,
) : ViewModel() {

    private val sortingOrder = getSortingOrderFlowUseCase.execute()
    private val sortingField = getSortingFieldFlowUseCase.execute()

    var images by mutableStateOf(getImagesFlow())
        private set
    val hasConnection = networkConnectionObserver.hasConnection
    var favoriteImagesIds: List<Int> by mutableStateOf(listOf())
        private set

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

        viewModelScope.launch {
            getFavoriteImagesIdsFlowUseCase.execute().collect {
                favoriteImagesIds = it
            }
        }
    }

    fun addToFavorite(image: ImageEntity) {
        viewModelScope.launch {
            addFavoriteImageUseCase.execute(image).fold(
                onSuccess = { Log.d("AAA", "OK") },
                onFailure = { Log.d("AAA", "NOK: ${it.message}")},
            )
        }
    }

    fun removeFromFavorite(image: ImageEntity) {
        viewModelScope.launch {
            removeFavoriteImageUseCase.execute(image.id).fold(
                onSuccess = { Log.d("AAA", "OK") },
                onFailure = { Log.d("AAA", "NOK: ${it.message}")},
            )
        }
    }

    private fun getImagesFlow() = getImagesPagingDataUseCase
        .execute()
        .cachedIn(viewModelScope)
}
