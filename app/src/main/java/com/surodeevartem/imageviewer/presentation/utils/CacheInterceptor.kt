package com.surodeevartem.imageviewer.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import coil.decode.DataSource
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.request.SuccessResult
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CacheInterceptor : Interceptor {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val filename = "${Base64.encode(chain.request.data.toString().encodeToByteArray())}.png"
        Log.d("AAA", chain.request.data.toString())
        Log.d("AAA", filename)
        val context = chain.request.context
        return try {
            val file = File(context.cacheDir, filename)
            if (file.isFile) {
                SuccessResult(
                    drawable = BitmapFactory.decodeFile(file.absolutePath).toDrawable(context.resources),
                    request = chain.request,
                    dataSource = DataSource.DISK,
                )
            } else {
                throw NoSuchFileException(file)
            }
        } catch (e: NoSuchFileException) {
            chain.proceed(chain.request).also {
                val createdFile = File(context.cacheDir, filename)
                it.drawable?.toBitmap()?.compress(Bitmap.CompressFormat.PNG, 100, createdFile.outputStream())
            }
        }
    }
}
