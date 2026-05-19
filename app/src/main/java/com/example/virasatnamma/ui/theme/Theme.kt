package com.example.virasatnamma.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ColorPalette = lightColors(
    primary = Saffron,
    primaryVariant = DeepSaffron,
    secondary = Gold,
    background = Background,
    surface = White,
    onPrimary = DarkBrown,
    onSecondary = DarkBrown,
    onBackground = TextDark,
    onSurface = TextDark,
)

@Composable
fun VirasatNammaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        content = content
    )
}
