package com.example.wallpaper.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import com.example.wallpaper.feature.category.presentation.theme.LocalDimension
import com.example.wallpaper.feature.category.presentation.theme.sw320
import com.example.wallpaper.feature.category.presentation.theme.sw320Typography
import com.example.wallpaper.feature.category.presentation.theme.sw600
import com.example.wallpaper.feature.category.presentation.theme.sw600Typography
import com.example.wallpaper.feature.category.presentation.theme.sw840
import com.example.wallpaper.feature.category.presentation.theme.sw840Typography

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = White,
    surface = LightBlack,
    background = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Pink,
    surface = White,
    background = White
)

@Composable
fun WallpaperTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val configuration = LocalConfiguration.current

    val dimens = when {
        configuration.smallestScreenWidthDp <= 320 -> sw320
        configuration.smallestScreenWidthDp >= 840 -> sw840
        else -> sw600
    }

    val typography = when {
        configuration.smallestScreenWidthDp <= 320 -> sw320Typography
        configuration.smallestScreenWidthDp >= 840 -> sw840Typography
        else -> sw600Typography
    }

    CompositionLocalProvider(LocalDimension provides dimens, LocalThemeInDark provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}

val LocalThemeInDark = compositionLocalOf { false }