package com.surodeevartem.imageviewer.presentation.main

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.surodeevartem.imageviewer.presentation.images.NavGraphs
import com.surodeevartem.imageviewer.presentation.images.destinations.RootImagesScreenDestination
import com.surodeevartem.imageviewer.presentation.images.root.RootImagesScreen
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme

@Composable
fun MainScreen() {
    ImageViewerTheme {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
        ) {
            composable(RootImagesScreenDestination) {
                RootImagesScreen(
                    imageCardClick = {

                    }
                )
            }
        }
    }
}
