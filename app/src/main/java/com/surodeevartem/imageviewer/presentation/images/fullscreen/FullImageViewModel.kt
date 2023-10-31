package com.surodeevartem.imageviewer.presentation.images.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surodeevartem.imageviewer.domain.AddFavoriteImageUseCase
import com.surodeevartem.imageviewer.domain.GetFavoriteImagesIdsFlowUseCase
import com.surodeevartem.imageviewer.domain.RemoveFavoriteImageUseCase
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.images.destinations.FullImageScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    savedStateHandle: SavedStateHandle,
    getFavoriteImagesIdsFlowUseCase: GetFavoriteImagesIdsFlowUseCase,
) : ViewModel() {
    val image: ImageEntity = FullImageScreenDestination.argsFrom(savedStateHandle)
    val isFavorite = getFavoriteImagesIdsFlowUseCase
        .execute()
        .map { it.contains(image.id)  }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onLikeClick() {
        viewModelScope.launch {
            if (isFavorite.value) {
                removeFavoriteImageUseCase.execute(image.id)
            } else {
                addFavoriteImageUseCase.execute(image)
            }
        }
    }
}
