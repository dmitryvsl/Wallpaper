package com.example.wallpaper.feature.category.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.wallpaper.core.model.CategoryType

data class Category(
    val type: CategoryType,
    @StringRes val title: Int,
    @DrawableRes val image: Int,
)
