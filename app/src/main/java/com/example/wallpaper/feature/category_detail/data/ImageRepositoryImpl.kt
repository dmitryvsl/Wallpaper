package com.example.wallpaper.feature.category_detail.data

import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor() : ImageRepository {

    override fun fetchImages(categoryType: CategoryType): DataState<List<Image>> {
        TODO("Not yet implemented")
    }
}