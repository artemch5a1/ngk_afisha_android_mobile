package com.example.ngkafisha.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryRedDark,
    onPrimary = Color.White,
    secondary = CardGrayDark,
    onSecondary = Color.White,
    tertiary = SuccessGreenDark,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF1A5C2E),
    onTertiaryContainer = SuccessGreenDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = BackgroundDark,
    onSurface = OnBackgroundDark,
    surfaceVariant = FieldGrayDark,
    onSurfaceVariant = Color.White,
    outline = FieldGrayDark,
    outlineVariant = CardGrayDark,
    error = Color(0xFFCF6679),
    onError = Color.White,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    secondaryContainer = SurfaceGrayDark,
    onSecondaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryRed,
    onPrimary = Color.White,
    secondary = CardGray,
    onSecondary = Color.Black,
    tertiary = SuccessGreen,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFB8E6C8),
    onTertiaryContainer = Color(0xFF00210A),
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = BackgroundLight,
    onSurface = OnBackgroundLight,
    surfaceVariant = FieldGray,
    onSurfaceVariant = Color.Black,
    outline = FieldGray,
    outlineVariant = SurfaceGray,
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    secondaryContainer = SurfaceGray,
    onSecondaryContainer = Color.Black
)

@Composable
fun NgkafishaTheme(
    darkTheme: Boolean = false,
    // Dynamic color отключен, используем кастомные цвета
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}