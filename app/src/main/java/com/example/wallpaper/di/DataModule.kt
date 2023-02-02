package com.example.wallpaper.di

import com.example.wallpaper.feature.category_detail.data.FakeImageRepository
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsImageRepository(repository: FakeImageRepository): ImageRepository
}