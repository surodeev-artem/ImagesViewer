package com.surodeevartem.imageviewer.entity

import androidx.compose.runtime.Immutable

@Immutable
data class ImageEntity(
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)
