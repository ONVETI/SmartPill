package uz.onveti.smartpill.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorPalette(
    val surfacePrimary: Color,
    val surfaceInputs: Color,
    val surfaceIcons: Color,
    val surfaceDisabled: Color,
    val surfaceSecondary: Color,
    val surfaceBackground1: Color,
    val surfaceBackground2: Color,

    val textDefaultH1Primary: Color,
    val textDefaultButton: Color,
    val textErrorDefault: Color,
    val textDefaultBodyPrimary: Color,

    val strokeDefault: Color,

    val iconsDefault: Color,
    val iconsDefault50: Color,
    val iconsButton: Color,
)

val LightColorPalette = AppColorPalette(
    surfacePrimary = Color(0xFF3183FF),
    surfaceInputs = Color(0xFFF6F6F6),
    surfaceIcons = Color(0xFFF6F6F6),
    surfaceDisabled = Color(0xFFD0D0D0),
    surfaceSecondary = Color(0x4DFDDD99),
    surfaceBackground1 = Color(0xFFFFFFFF),
    surfaceBackground2 = Color(0xFF0B0B0B),

    textDefaultH1Primary = Color(0xFF0B0B0B),
    textDefaultButton = Color(0xFFFFFFFF),
    textErrorDefault = Color(0xFFF91605),
    textDefaultBodyPrimary = Color(0xFF0B0B0B),

    strokeDefault = Color(0xFFE8E8E8),

    iconsDefault = Color(0xFF0B0B0B),
    iconsDefault50 = Color(0x660B0B0B),
    iconsButton = Color(0xFF0B0B0B),
)

val DarkColorPalette = AppColorPalette(
    surfacePrimary = Color(0xFF00B950),
    surfaceInputs = Color(0x14FFFFFF),
    surfaceIcons = Color(0x14FFFFFF),
    surfaceDisabled = Color(0xFF6E6E6E),
    surfaceSecondary = Color(0x1AFDD99D),
    surfaceBackground1 = Color(0xFF0F0F0F),
    surfaceBackground2 = Color(0xFF0B0B0B),

    textDefaultH1Primary = Color(0xFFFFFFFF),
    textDefaultButton = Color(0xFF0B0B0B),
    textErrorDefault = Color(0xFFFA335C),
    textDefaultBodyPrimary = Color(0xFFFFFFFF),

    strokeDefault = Color(0xFF292929),

    iconsDefault = Color(0xFFFFFFFF),
    iconsDefault50 = Color(0x66FFFFFF),
    iconsButton = Color(0xFF0B0B0B),
)

internal val LocalAppColors = staticCompositionLocalOf { LightColorPalette }