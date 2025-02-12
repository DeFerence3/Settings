package com.diffy.settings.ui.core

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.diffy.settings.R
import com.diffy.settings.ui.theme.conf.DarkTheme
import com.diffy.settings.ui.theme.conf.ThemeType

data class ThemePreference(
    val darkMode: DarkTheme = DarkTheme.System,
    val isHighContrastModeEnabled: Boolean = false,
    val themeType: ThemeType = ThemeType.Default
) {

    @Composable
    fun isDarkTheme(): Boolean {
        val darkModeStatus = when(darkMode) {
            DarkTheme.System -> isSystemInDarkTheme()
            DarkTheme.On -> true
            DarkTheme.Off -> false
        }
        return darkModeStatus
    }

    fun Context.getDarkThemeDesc(): String {
        return when (darkMode) {
            DarkTheme.System -> getString(R.string.follow_system)
            DarkTheme.On -> getString(R.string.on)
            DarkTheme.Off -> getString(R.string.off)
        }
    }
}