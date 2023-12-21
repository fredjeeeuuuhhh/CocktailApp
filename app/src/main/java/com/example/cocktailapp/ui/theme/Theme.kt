package com.example.cocktailapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = orange80,
    onPrimary = orange20,
    primaryContainer = orange30,
    onPrimaryContainer = orange90,
    inversePrimary = orange40,

    secondary = flame80,
    onSecondary = flame20,
    secondaryContainer = flame30,
    onSecondaryContainer = flame90,

    tertiary = yellow80,
    onTertiary = yellow20,
    tertiaryContainer = yellow30,
    onTertiaryContainer = yellow90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = brown10,
    onBackground = brown90,

    surface = brown30,
    onSurface = brown80,
    inverseSurface = brown90,
    inverseOnSurface = brown10,

    surfaceVariant = brown30,
    onSurfaceVariant = brown80,

    outline = brown80,
)

private val LightColorScheme = lightColorScheme(
    primary = orange40,
    onPrimary = Color.White,
    primaryContainer = orange90,
    onPrimaryContainer = orange10,
    inversePrimary = orange80,

    secondary = flame40,
    onSecondary = Color.White,
    secondaryContainer = flame90,
    onSecondaryContainer = flame10,

    tertiary = yellow40,
    onTertiary = Color.White,
    tertiaryContainer = yellow90,
    onTertiaryContainer = yellow10,

    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = brown90,
    onBackground = brown10,

    surface = brown90,
    onSurface = brown30,
    inverseSurface = brown20,
    inverseOnSurface = brown90,

    surfaceVariant = brown80,
    onSurfaceVariant = brown30,

    outline = brown40,

)

@Composable
fun CocktailAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
