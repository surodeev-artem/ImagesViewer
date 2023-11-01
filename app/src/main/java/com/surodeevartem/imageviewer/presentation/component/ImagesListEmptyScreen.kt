package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme
import com.surodeevartem.imageviewer.presentation.theme.Typography
import com.surodeevartem.imageviewer.presentation.utils.ImageViewerPreview

@Composable
fun ImagesListEmptyScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = R.string.images_not_found),
            style = Typography.titleMedium,
        )
    }
}

@ImageViewerPreview
@Composable
private fun ImagesListEmptyScreenPreview() {
    ImageViewerTheme {
        Surface {
            ImagesListEmptyScreen()
        }
    }
}
