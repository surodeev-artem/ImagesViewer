package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme
import com.surodeevartem.imageviewer.presentation.utils.ImageViewerPreview
import com.surodeevartem.imageviewer.utils.NoInternetException

@Composable
fun ImagesLoadingError(
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
private fun ImageLoadingErrorPreview() {
    ImageViewerTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ImagesLoadingError(
                    error = NoInternetException(),
                    onRetryClick = {},
                )
                Spacer(modifier = Modifier.height(16.dp))
                ImagesLoadingError(
                    error = RuntimeException(),
                    onRetryClick = {},
                )
            }
        }
    }
}
