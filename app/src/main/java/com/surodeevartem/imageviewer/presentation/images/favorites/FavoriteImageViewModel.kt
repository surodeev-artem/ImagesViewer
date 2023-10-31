package com.surodeevartem.imageviewer.presentation.images.favorites

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surodeevartem.imageviewer.domain.GetFavoriteImagesUseCase
import com.surodeevartem.imageviewer.domain.GetSortingFieldFlowUseCase
import com.surodeevartem.imageviewer.domain.GetSortingOrderFlowUseCase
import com.surodeevartem.imageviewer.domain.RemoveFavoriteImageUseCase
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.utils.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteImageViewModel @Inject constructor(
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
    getSortingFieldFlowUseCase: GetSortingFieldFlowUseCase,
    getSortingOrderFlowUseCase: GetSortingOrderFlowUseCase,
) : ViewModel() {

    private val sortingOrder = getSortingOrderFlowUseCase.execute()
    private val sortingField = getSortingFieldFlowUseCase.execute()

    var favoriteImages: Flow<List<ImageEntity>> by mutableStateOf(getFavoriteImagesUseCase.execute())
        private set

    init {
        viewModelScope.launch {
            sortingOrder.collect {
                favoriteImages = getFavoriteImagesUseCase.execute()
            }
        }

        viewModelScope.launch {
            sortingField.collect {
                favoriteImages = getFavoriteImagesUseCase.execute()
            }
        }
    }


    fun removeFromFavorite(image: ImageEntity) {
        viewModelScope.launch {
            removeFavoriteImageUseCase.execute(image.id).fold(
                onSuccess = {

                },
                onFailure = { Log.d("AAA", "NOK: ${it.message}") },
            )
        }
    }
}
