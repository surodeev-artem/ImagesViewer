package com.surodeevartem.imageviewer.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import com.surodeevartem.imageviewer.presentation.images.NavGraphs
import com.surodeevartem.imageviewer.presentation.images.destinations.FullImageScreenDestination
import com.surodeevartem.imageviewer.presentation.images.destinations.RootImagesScreenDestination
import com.surodeevartem.imageviewer.presentation.images.fullscreen.FullImageScreen
import com.surodeevartem.imageviewer.presentation.images.root.RootImagesScreen
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme

@Composable
fun MainScreen() {
    ImageViewerTheme {
        val navController = rememberNavController()
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
        ) {
            composable(RootImagesScreenDestination) {
                RootImagesScreen(
                    imageCardClick = {
                        navController.navigate(FullImageScreenDestination(it)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(FullImageScreenDestination) {
                FullImageScreen(
                    onBackClick = navController::popBackStack,
                )
            }
        }
    }
}
