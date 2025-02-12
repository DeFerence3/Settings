package com.diffy.settings.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.diffy.settings.ui.core.LocalDeveloperMode
import com.diffy.settings.ui.core.LocalThemePreference
import com.diffy.settings.ui.core.SettingsSaver
import com.diffy.settings.ui.theme.conf.ThemeType
import com.diffy.settings.ui.theme.conf.ThemeType.Default
import com.diffy.settings.ui.theme.conf.ThemeType.System
import com.diffy.settings.ui.theme.conf.ThemeType.Golden
import com.diffy.settings.ui.theme.golden.habtoorDarkScheme
import com.diffy.settings.ui.theme.golden.habtoorLightScheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SettingsTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isHighContrastModeEnabled: Boolean = false,
    themeType: ThemeType = Default,
    content: @Composable () -> Unit,
) {

    val colorScheme = getTheme(themeType,isDarkTheme).run {
        if (isHighContrastModeEnabled && isDarkTheme){
            copy(
                surface = Color.Black,
                background = Color.Black,
                surfaceContainerLowest = Color.Black,
                surfaceContainerLow = surfaceContainerLowest,
                surfaceContainer = surfaceContainerLow,
                surfaceContainerHigh = surfaceContainerLow,
                surfaceContainerHighest = surfaceContainer,
            )
        }
        else this
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
fun SettingsProvider(content: @Composable () -> Unit){
    SettingsSaver.AppSettingsStateFlow.collectAsState().value.run {
        CompositionLocalProvider(
            LocalDeveloperMode provides isDeveloperMode,
            LocalThemePreference provides themePreference
        ){
            content()
        }
    }
}

@Composable
fun getTheme(themeType: ThemeType, isDarkMode: Boolean): ColorScheme {
    return when(themeType){
        Golden -> if (isDarkMode) habtoorDarkScheme else habtoorLightScheme
        Default -> if (isDarkMode) darkColorScheme() else lightColorScheme()
        System -> when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            isDarkMode -> DarkColorScheme
            else -> LightColorScheme
        }
    }
}