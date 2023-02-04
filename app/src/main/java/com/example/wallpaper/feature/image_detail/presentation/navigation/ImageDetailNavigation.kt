package com.example.wallpaper.feature.image_detail.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.wallpaper.feature.image_detail.presentation.ImageDetailScreen

private const val imageDetailRoute = "image_detail_route"

fun NavController.navigateToImageDetail( navOptions: NavOptions? = null) {
    this.navigate(imageDetailRoute, navOptions)
}

fun NavGraphBuilder.imageDetail(
    onBackClick: () -> Unit
) {
    composable(route = imageDetailRoute,) {
        ImageDetailScreen(onBackClick = onBackClick)
    }
}