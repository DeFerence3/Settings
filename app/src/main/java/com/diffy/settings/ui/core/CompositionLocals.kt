package com.diffy.settings.ui.core

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDeveloperMode = staticCompositionLocalOf { false }
val LocalThemePreference = compositionLocalOf { ThemePreference() }