package com.surodeevartem.imageviewer.presentation.images.root

import android.media.Image
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.images.NavGraphs
import com.surodeevartem.imageviewer.presentation.images.SortingModalBottomSheet
import com.surodeevartem.imageviewer.presentation.images.all.AllImagesScreen
import com.surodeevartem.imageviewer.presentation.images.all.AllImagesViewModel
import com.surodeevartem.imageviewer.presentation.images.destinations.AllImagesScreenDestination
import com.surodeevartem.imageviewer.presentation.images.destinations.FavoritesImagesScreenDestination
import com.surodeevartem.imageviewer.presentation.images.favorites.FavoritesImagesScreen

private val items = listOf(
    Screen.AllImages,
    Screen.FavoriteImages,
)

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun RootImagesScreen(
    imageCardClick: (image: ImageEntity) -> Unit,
) {
    val viewModel = hiltViewModel<RootImagesViewModel>()
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var titleTextId by remember { mutableIntStateOf(R.string.all_images) }

    var showSortingBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = titleTextId)) },
                actions = {
                    IconButton(onClick = { showSortingBottomSheet = true }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            TabRow(selectedTabIndex = selectedTabIndex) {
                items.forEachIndexed { index, screen ->
                    Tab(
                        text = { Text(stringResource(screen.title)) },
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                            titleTextId = screen.title
                            navController.navigate(screen.direction) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
            DestinationsNavHost(
                navGraph = NavGraphs.rootImages,
                navController = navController,
            ) {
                composable(AllImagesScreenDestination) {
                    AllImagesScreen(
                        imageCardClick = imageCardClick,
                    )
                }
                composable(FavoritesImagesScreenDestination) {
                    FavoritesImagesScreen()
                }
            }
        }

        if (showSortingBottomSheet) {
            val sortingField by viewModel.sortingField.collectAsState()
            val sortingOrder by viewModel.sortingOrder.collectAsState()

            SortingModalBottomSheet(
                onDismissRequest = { showSortingBottomSheet = false },
                sheetState = sheetState,
                currentSortingField = sortingField,
                currentSortingOrder = sortingOrder,
                onSortingFieldChange = viewModel::changeSortingField,
                onSortingOrderChange = viewModel::changeSortingOrder,
            )
        }
    }
}


private sealed class Screen(
    val direction: DirectionDestinationSpec,
    @StringRes val title: Int,
    val icon: ImageVector,
) {
    data object AllImages : Screen(AllImagesScreenDestination, R.string.all_images, Icons.Filled.List)
    data object FavoriteImages : Screen(FavoritesImagesScreenDestination, R.string.favorite_images, Icons.Filled.Favorite)
}
