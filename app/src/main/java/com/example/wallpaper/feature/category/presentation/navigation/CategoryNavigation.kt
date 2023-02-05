package com.example.wallpaper.feature.category.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.feature.category.presentation.CategoryScreen

const val category_route = "category_route"

fun NavGraphBuilder.category(
    isScreenSizeCompact: Boolean,
    navigateToCategoryDetail: (CategoryType) -> Unit,
    onThemeChange: (Boolean) -> Unit,
) {
    composable(route = category_route) {
        CategoryScreen(
            isScreenSizeCompact = isScreenSizeCompact,
            navigateToCategoryDetail = navigateToCategoryDetail,
            onThemeChange = onThemeChange
        )
    }
}