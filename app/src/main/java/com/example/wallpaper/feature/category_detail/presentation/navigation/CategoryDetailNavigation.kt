package com.example.wallpaper.feature.category_detail.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.feature.category_detail.presentation.CategoryDetailScreen

private const val categoryDetailRoute = "category_detail_route/{type}"

fun NavController.navigateToCategoryDetail(
    categoryType: CategoryType,
    navOptions: NavOptions? = null
) {
    this.navigate(
        categoryDetailRoute.replace("{type}", categoryType.type.toString()),
        navOptions
    )
}

fun NavGraphBuilder.categoryDetail(
    onBackClick: () -> Unit,
    onThemeChange: (Boolean) -> Unit,
    navigateToImageDetail: () -> Unit,
) {
    composable(
        route = categoryDetailRoute,
        arguments = listOf(navArgument("type") { type = NavType.IntType })
    ) { backStackEntry ->
        val intType = backStackEntry.arguments!!.getInt("type")
        val type = CategoryType.values().first { it.type == intType }
        CategoryDetailScreen(
            categoryType = type,
            onBackClick = onBackClick,
            navigateToImageDetail = navigateToImageDetail,
            onThemeChange = onThemeChange
        )
    }
}
