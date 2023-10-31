package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import com.surodeevartem.imageviewer.presentation.utils.CacheInterceptor

@Composable
fun AsyncCustomCachingImage(
    url: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholder: Painter? = null,
) {
    val context = LocalContext.current
    AsyncImage(
        model = url,
        imageLoader = remember {
            ImageLoader.Builder(context)
                .components {
                    add(CacheInterceptor())
                }
                .build()
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeholder,
        modifier = modifier,
    )
}
