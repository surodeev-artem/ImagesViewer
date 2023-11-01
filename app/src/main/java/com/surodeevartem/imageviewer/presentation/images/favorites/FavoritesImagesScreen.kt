package com.surodeevartem.imageviewer.presentation.images.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.component.ImageCard
import com.surodeevartem.imageviewer.presentation.component.ImagesListEmptyScreen
import com.surodeevartem.imageviewer.presentation.navigation.RootImagesNavGraph
import com.surodeevartem.imageviewer.presentation.transition.FadeTransition
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
@RootImagesNavGraph
@Destination(style = FadeTransition::class)
@Composable
fun FavoritesImagesScreen(
    imageCardClick: (image: ImageEntity) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel = hiltViewModel<FavoriteImageViewModel>()
    val images by viewModel.favoriteImages.collectAsState(initial = persistentListOf())

    LaunchedEffect(Unit) {
        viewModel.snackbarState.collect {
            when (it) {
                FavoriteImagesScreenSnackbarState.REMOVED -> {
                    val result = snackbarHostState.showSnackbar(
                        message = "Удалено из избранного",
                        duration = SnackbarDuration.Short,
                        actionLabel = "Отмена",
                    )

                    when (result) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> viewModel.undoRemoving()
                    }
                }
            }
        }
    }
    val hiddenImage = viewModel.hiddenImage
    Content(
        hiddenImage = viewModel.hiddenImage,
        images = images,
        imageCardClick = imageCardClick,
        removeFromFavorite = viewModel::removeFromFavorite,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    hiddenImage: ImageEntity?,
    images: ImmutableList<ImageEntity>,
    imageCardClick: (ImageEntity) -> Unit,
    removeFromFavorite: (ImageEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val filteredImages = remember(images, hiddenImage) {
        if (hiddenImage == null) images else images.filter { it.id != hiddenImage.id }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            count = filteredImages.size,
            key = { filteredImages[it].id },
        ) { index ->
            val image = filteredImages[index]
            ImageCard(
                id = image.id,
                url = image.thumbnailUrl,
                title = image.title,
                isFavorite = true,
                onClick = { imageCardClick(image) },
                onLikeClick = { removeFromFavorite(image) },
                modifier = Modifier.animateItemPlacement(),
            )
        }

        if (filteredImages.isEmpty()) {
            item {
                ImagesListEmptyScreen(modifier = Modifier.fillParentMaxSize())
            }
        }
    }
}
