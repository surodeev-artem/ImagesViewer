package com.surodeevartem.imageviewer.presentation.images.favorites

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.surodeevartem.imageviewer.presentation.navigation.RootImagesNavGraph

@RootImagesNavGraph
@Destination
@Composable
fun FavoritesImagesScreen() {
    Spacer(modifier = Modifier.fillMaxSize())
}
