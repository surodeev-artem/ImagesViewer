package com.surodeevartem.imageviewer.presentation.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
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
        modifier = modifier.shimmerEffect(),
    )
}

private fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    val baseColor = MaterialTheme.colorScheme.primary
    val backgroundColor = baseColor.copy(
        baseColor.alpha,
        baseColor.red - 0.3f,
        baseColor.green - 0.3f,
        baseColor.blue - 0.3f,
    )
    val accentColor = baseColor.copy(
        baseColor.alpha,
        baseColor.red - 0.15f,
        baseColor.green - 0.15f,
        baseColor.blue - 0.15f,
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                backgroundColor,
                accentColor,
                backgroundColor,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
