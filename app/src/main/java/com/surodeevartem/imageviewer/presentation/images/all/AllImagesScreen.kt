package com.surodeevartem.imageviewer.presentation.images.all

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.entity.ImageEntity
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme
import com.surodeevartem.imageviewer.presentation.utils.ImageViewerPreview
import com.surodeevartem.imageviewer.utils.NoInternetException

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllImagesScreen(
    modifier: Modifier = Modifier,
    viewModel: AllImagesViewModel,
) {
    val images = viewModel.images.collectAsLazyPagingItems()

    val isLoading by remember {
        derivedStateOf { images.loadState.refresh is LoadState.Loading && images.itemCount > 0 }
    }

    val pullRefreshState = rememberPullRefreshState(
        isLoading,
        images::refresh,
    )

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        Content(images = images)
        PullRefreshIndicator(
            isLoading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(images: LazyPagingItems<ImageEntity>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = images.itemCount,
            key = images.itemKey { it.id },
        ) { index ->
            val image = images[index] ?: return@items
            Card(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {
                    AsyncImage(
                        model = image.thumbnailUrl,
                        contentDescription = image.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    )
                    Text(
                        text = image.title,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }

        when (val state = images.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    ErrorHandler(
                        error = state.error,
                        modifier = Modifier.fillParentMaxSize(),
                        onRetryClick = images::retry,
                    )
                }
            }

            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            else -> {}
        }

        when (val state = images.loadState.append) {
            is LoadState.Error -> {
                item {
                    ErrorHandler(
                        error = state.error,
                        modifier = Modifier.fillMaxWidth(),
                        onRetryClick = images::retry,
                    )
                }
            }

            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun ErrorHandler(
    error: Throwable,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = when (error) {
                is NoInternetException -> painterResource(id = R.drawable.ic_no_connection)
                else -> painterResource(id = R.drawable.ic_server_error)
            },
            contentDescription = null,
            modifier = Modifier.size(96.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
        )
        Text(
            text = when (error) {
                is NoInternetException -> stringResource(id = R.string.no_internet)
                else -> stringResource(id = R.string.no_internet)
            },
        )
        TextButton(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@ImageViewerPreview
@Composable
private fun ErrorHandlerPreview() {
    ImageViewerTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ErrorHandler(
                    error = NoInternetException(),
                    onRetryClick = {},
                )
                Spacer(modifier = Modifier.height(16.dp))
                ErrorHandler(
                    error = RuntimeException(),
                    onRetryClick = {},
                )
            }
        }
    }
}
