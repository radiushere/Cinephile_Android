package com.example.cinephile.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class AppTheme { Default, Ocean, Forest }

private val DarkDefaultColorScheme = darkColorScheme(
    primary = DarkPrimary, onPrimary = DarkOnPrimary, background = DarkBackground,
    surface = DarkSurface, onSurface = DarkOnSurface
)

private val LightDefaultColorScheme = lightColorScheme(
    primary = LightPrimary, onPrimary = LightOnPrimary, background = LightBackground,
    surface = LightSurface, onSurface = LightOnSurface
)

private val OceanColorScheme = darkColorScheme(
    primary = OceanPrimary, onPrimary = OceanOnPrimary, background = OceanBackground,
    surface = OceanSurface, onSurface = OceanOnSurface
)

private val ForestColorScheme = darkColorScheme(
    primary = ForestPrimary, onPrimary = ForestOnPrimary, background = ForestBackground,
    surface = ForestSurface, onSurface = ForestOnSurface
)

@Composable
fun CinePhileTheme(
    theme: AppTheme = AppTheme.Default,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        AppTheme.Default -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
        AppTheme.Ocean -> OceanColorScheme
        AppTheme.Forest -> ForestColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}