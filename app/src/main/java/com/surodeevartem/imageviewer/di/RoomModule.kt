package com.surodeevartem.imageviewer.di

import android.content.Context
import androidx.room.Room
import com.surodeevartem.imageviewer.data.local.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideImagesDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = ImagesDatabase::class.java,
        name = "images",
    ).build()
}
