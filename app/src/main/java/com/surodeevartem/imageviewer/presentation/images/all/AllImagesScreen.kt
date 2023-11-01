package com.surodeevartem.imageviewer.presentation.images.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ramcosta.composedestinations.annotation.Destination
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.component.ImageCard
import com.surodeevartem.imageviewer.presentation.component.ImagesListEmptyScreen
import com.surodeevartem.imageviewer.presentation.component.ImagesLoadingError
import com.surodeevartem.imageviewer.presentation.component.ImagesLoadingIndicator
import com.surodeevartem.imageviewer.presentation.navigation.RootImagesNavGraph
import com.surodeevartem.imageviewer.presentation.transition.FadeTransition
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterialApi::class)
@RootImagesNavGraph(start = true)
@Destination(style = FadeTransition::class)
@Composable
fun AllImagesScreen(
    imageCardClick: (image: ImageEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<AllImagesViewModel>()
    val images = viewModel.images.collectAsLazyPagingItems()

    val isLoading by remember {
        derivedStateOf { images.loadState.refresh is LoadState.Loading && images.itemCount > 0 }
    }

    val pullRefreshState = rememberPullRefreshState(
        isLoading,
        images::refresh,
    )

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        Content(
            images = images,
            imageCardClick = imageCardClick,
            favoriteImagesId = viewModel.favoriteImagesIds,
            onLikeClick = viewModel::addToFavorite,
            onUnlikeClick = viewModel::removeFromFavorite,
        )
        PullRefreshIndicator(
            isLoading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun Content(
    images: LazyPagingItems<ImageEntity>,
    favoriteImagesId: ImmutableList<Int>,
    imageCardClick: (image: ImageEntity) -> Unit,
    onLikeClick: (image: ImageEntity) -> Unit,
    onUnlikeClick: (image: ImageEntity) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = images.itemCount,
            key = images.itemKey { it.id },
            contentType = { ImageEntity::class },
        ) { index ->
            val image = images[index] ?: return@items
            val isFavorite = remember(favoriteImagesId) {
                favoriteImagesId.contains(image.id)
            }
            ImageCard(
                id = image.id,
                url = image.thumbnailUrl,
                title = image.title,
                isFavorite = isFavorite,
                onClick = { imageCardClick(image) },
                onLikeClick = {
                    if (isFavorite) {
                        onUnlikeClick(image)
                    } else {
                        onLikeClick(image)
                    }
                }
            )
        }

        when (val state = images.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ImagesLoadingError(
                        error = state.error,
                        modifier = Modifier.fillParentMaxSize(),
                        onRetryClick = images::retry,
                    )
                }
            }

            is LoadState.Loading -> {
                item {
                    ImagesLoadingIndicator(
                        modifier = Modifier.fillParentMaxSize(),
                    )
                }
            }

            else -> {
                if (images.itemCount == 0) {
                    item {
                        ImagesListEmptyScreen(modifier = Modifier.fillParentMaxSize())
                    }
                }
            }
        }

        when (val state = images.loadState.append) {
            is LoadState.Error -> {
                item {
                    ImagesLoadingError(
                        error = state.error,
                        modifier = Modifier.fillMaxWidth(),
                        onRetryClick = images::retry,
                    )
                }
            }

            is LoadState.Loading -> {
                item {
                    ImagesLoadingIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                    )
                }
            }

            else -> {}
        }
    }
}
