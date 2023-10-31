package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.surodeevartem.imageviewer.R
import com.surodeevartem.imageviewer.presentation.theme.ImageViewerTheme
import com.surodeevartem.imageviewer.presentation.utils.ImageViewerPreview
import com.surodeevartem.imageviewer.presentation.utils.debugPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    url: String,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
    ) {
        Column {
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = debugPlaceholder(debugPreview = R.drawable.ic_image_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.8f),
            )
            Text(
                text = title,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@ImageViewerPreview
@Composable
private fun ImageCardPreview() {
    ImageViewerTheme {
        Surface {
            ImageCard(
                url = "ASDASDAS",
                title = "Test",
                onClick = {},
            )
        }
    }
}
