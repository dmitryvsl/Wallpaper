package com.example.wallpaper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.wallpaper.app.navigation.WallpaperNavigation
import com.example.wallpaper.app.theme.WallpaperTheme
import com.example.wallpaper.core.data.ThemeMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeMonitor: ThemeMonitor

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            if (isSystemInDarkTheme()) themeMonitor.saveDarkModeIfFirstLaunch()
            val isDarkTheme by themeMonitor.isDarkTheme().collectAsState()
            WallpaperTheme(isDarkTheme) {
                val systemUiController = rememberSystemUiController()
                DisposableEffect(systemUiController, isDarkTheme) {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isDarkTheme
                    )

                    onDispose {}
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WallpaperNavigation(
                        windowSizeClass = calculateWindowSizeClass(this),
                        onThemeChange = { isDark ->
                            if (isDark) themeMonitor.setDarkTheme() else themeMonitor.setLightTheme()
                        }
                    )
                }
            }
        }
    }
}
