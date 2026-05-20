package com.mermanconservation.jellyfishgr.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OceanBlue = Color(0xFF006994)
val OceanBlueDark = Color(0xFF004D6E)
val OceanBlueLight = Color(0xFF4D9FBF)
val PelagiaPurple = Color(0xFF8B3A8B)
val PelagiaPink = Color(0xFFD4618C)
val CoralOrange = Color(0xFFE07B39)
val SeaGreen = Color(0xFF2E8B57)
val SandBeige = Color(0xFFF5F0E8)
val DeepSea = Color(0xFF0A2342)
val FoamWhite = Color(0xFFF8FEFF)
val DangerRed = Color(0xFFD32F2F)
val WarnAmber = Color(0xFFF57C00)
val SafeGreen = Color(0xFF388E3C)

private val LightColorScheme = lightColorScheme(
    primary = OceanBlue,
    onPrimary = Color.White,
    primaryContainer = OceanBlueLight,
    onPrimaryContainer = DeepSea,
    secondary = PelagiaPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3D1F3),
    onSecondaryContainer = Color(0xFF3D0054),
    tertiary = SeaGreen,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFB7F0D0),
    onTertiaryContainer = Color(0xFF002114),
    background = FoamWhite,
    onBackground = DeepSea,
    surface = Color.White,
    onSurface = DeepSea,
    surfaceVariant = SandBeige,
    onSurfaceVariant = Color(0xFF4A4040),
    error = DangerRed,
    outline = Color(0xFF8A7F80)
)

private val DarkColorScheme = darkColorScheme(
    primary = OceanBlueLight,
    onPrimary = DeepSea,
    primaryContainer = OceanBlueDark,
    onPrimaryContainer = Color(0xFFB8E4FF),
    secondary = PelagiaPink,
    onSecondary = Color(0xFF3D0054),
    secondaryContainer = Color(0xFF5A1F6B),
    onSecondaryContainer = Color(0xFFF3D1F3),
    tertiary = Color(0xFF8DD5AA),
    onTertiary = Color(0xFF003824),
    background = Color(0xFF0D1B2A),
    onBackground = Color(0xFFE2E8F0),
    surface = Color(0xFF1A2744),
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = Color(0xFF2A3A54),
    error = Color(0xFFFF8A8A)
)

@Composable
fun JellyfishGRTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
