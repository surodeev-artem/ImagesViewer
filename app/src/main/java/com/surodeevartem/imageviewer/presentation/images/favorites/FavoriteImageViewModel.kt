package com.surodeevartem.imageviewer.presentation.images.favorites

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteImageViewModel @Inject constructor(
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
    getSortingFieldFlowUseCase: GetSortingFieldFlowUseCase,
    getSortingOrderFlowUseCase: GetSortingOrderFlowUseCase,
) : ViewModel() {
    private val _snackbarState: MutableSharedFlow<FavoriteImagesScreenSnackbarState> = MutableSharedFlow()
    val snackbarState: SharedFlow<FavoriteImagesScreenSnackbarState> = _snackbarState.asSharedFlow()

    private val sortingOrder = getSortingOrderFlowUseCase.execute()
    private val sortingField = getSortingFieldFlowUseCase.execute()

    private var currentRemovingJob: Job? = null
    var hiddenImage: ImageEntity? by mutableStateOf(null)

    private var lastRemovedImage: ImageEntity? = null
        set(value) {
            if (value != null && field != null) {
                viewModelScope.launch {
                    val currentField = field ?: return@launch
                    currentRemovingJob?.cancel()
                    removeFavoriteImageUseCase.execute(currentField.id)
                }
            }
            field = value
            hiddenImage = value
        }

    var favoriteImages: Flow<ImmutableList<ImageEntity>> by mutableStateOf(getFavoriteImagesUseCase.execute())
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
        lastRemovedImage = image
        currentRemovingJob = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Default) {
                _snackbarState.emit(FavoriteImagesScreenSnackbarState.REMOVED)
            }
            delay(SNACKBAR_DURATION)
            removeFavoriteImageUseCase.execute(image.id)
            lastRemovedImage = null
        }
    }

    fun undoRemoving() {
        lastRemovedImage = null
        currentRemovingJob?.cancel()
        currentRemovingJob = null
    }

    companion object {
        // Short duration of the snackbar
        private const val SNACKBAR_DURATION = 4000L
    }
}

enum class FavoriteImagesScreenSnackbarState {
    REMOVED,
}
