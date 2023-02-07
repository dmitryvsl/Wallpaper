package com.example.wallpaper.core.data

import kotlinx.coroutines.flow.StateFlow

interface ThemeMonitor {

    fun isDarkTheme(): StateFlow<Boolean>

    fun setDarkTheme()

    fun saveDarkModeIfFirstLaunch()

    fun setLightTheme()
}
