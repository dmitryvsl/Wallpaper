package com.example.wallpaper.app.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.wallpaper.feature.category.presentation.navigation.category
import com.example.wallpaper.feature.category.presentation.navigation.category_route
import com.example.wallpaper.feature.image_list.presentation.navigation.categoryDetail
import com.example.wallpaper.feature.image_list.presentation.navigation.navigateToCategoryDetail
import com.example.wallpaper.feature.image_detail.presentation.navigation.imageDetail
import com.example.wallpaper.feature.image_detail.presentation.navigation.navigateToImageDetail


@Composable
fun WallpaperNavigation(
    windowSizeClass: WindowSizeClass,
    onThemeChange: (Boolean) -> Unit,
) {
    val navController = rememberNavController()

    val isScreenSizeCompact =
        remember { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact }
    NavHost(
        navController = navController,
        startDestination = category_route
    ) {
        category(
            isScreenSizeCompact = isScreenSizeCompact,
            navigateToCategoryDetail = {navController.navigateToCategoryDetail(it)},
            onThemeChange = onThemeChange
        )

        categoryDetail(
            onBackClick = { navController.navigateUp() },
            onThemeChange = onThemeChange
        ) { navController.navigateToImageDetail() }

        imageDetail { navController.navigateUp() }
    }
}