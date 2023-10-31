package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    isFavorite: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                )
                IconButton(onClick = onLikeClick) {
                    if (isFavorite) {
                        Icon(Icons.Filled.Favorite, null)
                    } else {
                        Icon(Icons.Filled.FavoriteBorder, null)
                    }
                }
            }
        }
    }
}

@ImageViewerPreview
@Composable
private fun ImageCardPreview() {
    ImageViewerTheme {
        Surface {
            Column {
                ImageCard(
                    url = "",
                    title = "Test",
                    isFavorite = false,
                    onClick = {},
                    onLikeClick = {},
                )
                ImageCard(
                    url = "",
                    title = "Test",
                    isFavorite = true,
                    onClick = {},
                    onLikeClick = {},
                )
            }
        }
    }
}
