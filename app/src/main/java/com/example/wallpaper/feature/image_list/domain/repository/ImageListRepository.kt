package com.example.wallpaper.feature.image_list.domain.repository

import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.image_list.domain.model.Image

interface ImageListRepository {
    suspend fun fetchImages(categoryType: CategoryType, page: Int): DataState<List<Image>>

}