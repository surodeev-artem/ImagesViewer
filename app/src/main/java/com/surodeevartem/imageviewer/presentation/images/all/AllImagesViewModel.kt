package com.surodeevartem.imageviewer.presentation.images.all

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surodeevartem.imageviewer.entity.Image
import com.surodeevartem.imageviewer.repository.remote.ImagesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllImagesViewModel @Inject constructor(
    private val imagesService: ImagesService,
): ViewModel() {
    var images by mutableStateOf<List<Image>>(listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            images = imagesService.pagedImages(0, 20, "id", "asc")
        }
    }
}
