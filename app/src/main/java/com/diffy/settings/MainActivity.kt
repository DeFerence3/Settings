package com.diffy.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.diffy.settings.ui.core.LocalThemePreference
import com.diffy.settings.ui.settings.SettingsScreen
import com.diffy.settings.ui.theme.SettingsProvider
import com.diffy.settings.ui.theme.SettingsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SettingsProvider{
                SettingsTheme(
                    isDarkTheme = LocalThemePreference.current.isDarkTheme(),
                    isHighContrastModeEnabled = LocalThemePreference.current.isHighContrastModeEnabled,
                    themeType = LocalThemePreference.current.themeType
                ) {
                    SettingsScreen()
                }
            }
        }
    }
}