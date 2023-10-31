package com.surodeevartem.imageviewer.presentation.images.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.images.destinations.FullImageScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val image: ImageEntity = FullImageScreenDestination.argsFrom(savedStateHandle)
}
