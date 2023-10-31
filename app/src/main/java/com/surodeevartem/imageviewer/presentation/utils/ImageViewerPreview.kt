package com.surodeevartem.imageviewer.presentation.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@Preview(
    name = "Day Mode",
    device = "id:pixel_4",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    device = "id:pixel_4",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode Monochrome",
    device = "id:pixel_4",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
)
@Preview(
    name = "Night Mode Monochrome",
    device = "id:pixel_4",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
)
annotation class ImageViewerPreview
