package com.surodeevartem.imageviewer.presentation.images.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surodeevartem.imageviewer.domain.GetFavoriteImagesUseCase
import com.surodeevartem.imageviewer.entity.ImageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteImageViewModel @Inject constructor(
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
) : ViewModel() {
    var favoriteImages: List<ImageEntity> by mutableStateOf(listOf())
        private set

    init {
        fetchFavoriteImages()
    }

    private fun fetchFavoriteImages() {
        viewModelScope.launch {
            favoriteImages = getFavoriteImagesUseCase.execute()
        }
    }
}
