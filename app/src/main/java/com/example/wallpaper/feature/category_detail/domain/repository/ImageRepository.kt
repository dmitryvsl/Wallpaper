package com.example.wallpaper.feature.category_detail.domain.repository

import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.Image

interface ImageRepository {
    suspend fun fetchImages(categoryType: CategoryType, page: Int): DataState<List<Image>>
}