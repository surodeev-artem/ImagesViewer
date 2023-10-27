package com.surodeevartem.imageviewer.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Image(
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)
