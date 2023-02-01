package com.example.wallpaper.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    surface = LightBlack,
    background = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    surface = White,
    background = White
)

@Composable
fun WallpaperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

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

    CompositionLocalProvider(LocalDimension provides dimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}