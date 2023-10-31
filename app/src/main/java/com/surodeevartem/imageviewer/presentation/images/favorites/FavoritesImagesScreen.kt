package com.surodeevartem.imageviewer.presentation.images.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.surodeevartem.imageviewer.presentation.component.ImageCard
import com.surodeevartem.imageviewer.presentation.navigation.RootImagesNavGraph

@RootImagesNavGraph
@Destination
@Composable
fun FavoritesImagesScreen() {
    val viewModel = hiltViewModel<FavoriteImageViewModel>()
    val images = viewModel.favoriteImages
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = images.size,
            key = { images[it].id },
        ) { index ->
            val image = images[index] ?: return@items
            ImageCard(
                url = image.thumbnailUrl,
                title = image.title,
                isFavorite = true,
                onClick = { },
                onLikeClick = { }
            )
        }
    }
}
