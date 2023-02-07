package com.example.wallpaper.core.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val PREFS_NAME = "prefs_name"
private const val PREFS_THEME = "prefs_name"
private const val PREFS_FIRST_LAUNCH = "prefs_first_launch"

class ThemeConfigurator @Inject constructor(
    @ApplicationContext context: Context,
) : ThemeMonitor {

    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val isDarkTheme: MutableStateFlow<Boolean> = MutableStateFlow(getThemeFromStorage())

    override fun isDarkTheme(): StateFlow<Boolean> = isDarkTheme

    override fun setDarkTheme() {
        isDarkTheme.value = true
        saveTheme(true)
    }

    override fun saveDarkModeIfFirstLaunch() {
        if (sharedPrefs.getBoolean(PREFS_FIRST_LAUNCH, true)){
            sharedPrefs.edit().apply {
                putBoolean(PREFS_FIRST_LAUNCH, false)
                apply()
            }
            saveTheme(true)
        }
    }

    override fun setLightTheme() {
        isDarkTheme.value = false
        saveTheme(false)
    }

    private fun saveTheme(isDark: Boolean){
        sharedPrefs.edit().apply{
            putBoolean(PREFS_THEME, isDark)
            apply()
        }
    }

    private fun getThemeFromStorage(): Boolean = sharedPrefs.getBoolean(PREFS_THEME, false)
}