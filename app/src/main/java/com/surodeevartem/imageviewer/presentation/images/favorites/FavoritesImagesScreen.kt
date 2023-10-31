package com.surodeevartem.imageviewer.presentation.images.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.component.ImageCard
import com.surodeevartem.imageviewer.presentation.navigation.RootImagesNavGraph
import com.surodeevartem.imageviewer.presentation.transition.FadeTransition

@OptIn(ExperimentalFoundationApi::class)
@RootImagesNavGraph
@Destination(style = FadeTransition::class)
@Composable
fun FavoritesImagesScreen(
    imageCardClick: (image: ImageEntity) -> Unit,
) {
    val viewModel = hiltViewModel<FavoriteImageViewModel>()
    val images by viewModel.favoriteImages.collectAsState(initial = listOf())
    val scrollState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
    ) {
        items(
            count = images.size,
            key = { images[it].id },
        ) { index ->
            val image = images[index]
            ImageCard(
                url = image.thumbnailUrl,
                title = image.title,
                isFavorite = true,
                onClick = { imageCardClick(image) },
                onLikeClick = { viewModel.removeFromFavorite(image) },
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
}
