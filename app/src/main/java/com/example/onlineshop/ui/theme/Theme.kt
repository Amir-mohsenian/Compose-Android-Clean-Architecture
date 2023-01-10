package com.example.onlineshop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = BlackBlue200,
    primaryVariant = BlackBlue700,
    secondary = Red200,
    background = BlackBlue200
)

private val LightColorPalette = lightColors(
    primary = BlackBlue500,
    primaryVariant = BlackBlue700,
    secondary = Red200,
    background = Gray500,
    onBackground = BlackBlue500
)

@Composable
fun OnlineShopTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}