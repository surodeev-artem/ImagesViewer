package com.surodeevartem.imageviewer.presentation.images.all

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
    var favoriteImagesIds: ImmutableList<Int> by mutableStateOf(persistentListOf())
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
                favoriteImagesIds = it.toImmutableList()
            }
        }
    }

    fun addToFavorite(image: ImageEntity) {
        viewModelScope.launch {
            addFavoriteImageUseCase.execute(image)
        }
    }

    fun removeFromFavorite(image: ImageEntity) {
        viewModelScope.launch {
            removeFavoriteImageUseCase.execute(image.id)
        }
    }

    private fun getImagesFlow() = getImagesPagingDataUseCase
        .execute()
        .cachedIn(viewModelScope)
}
