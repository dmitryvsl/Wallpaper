package com.example.wallpaper.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import com.example.wallpaper.feature.category.presentation.navigation.category
import com.example.wallpaper.feature.category.presentation.navigation.category_route
import com.example.wallpaper.feature.category_detail.presentation.navigation.categoryDetail
import com.example.wallpaper.feature.category_detail.presentation.navigation.navigateToCategoryDetail
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WallpaperNavigation(
    windowSizeClass: WindowSizeClass,
) {
    val navController = rememberAnimatedNavController()

    val isScreenSizeCompact =
        remember { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact }
    NavHost(
        navController = navController,
        startDestination = category_route
    ) {
        category(isScreenSizeCompact) { navController.navigateToCategoryDetail(it) }
        categoryDetail { navController.navigateUp() }
    }
}