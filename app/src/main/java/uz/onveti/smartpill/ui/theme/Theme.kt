package uz.onveti.smartpill.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)


private val DarkMaterialColorScheme = darkColorScheme(
    primary = DarkColorPalette.surfacePrimary,
    onPrimary = DarkColorPalette.textDefaultButton,
    secondary = DarkColorPalette.surfaceSecondary,
    onSecondary = DarkColorPalette.textDefaultBodyPrimary,
    error = DarkColorPalette.textErrorDefault,
    onError = Color.White,
    background = DarkColorPalette.surfaceBackground1,
    onBackground = DarkColorPalette.textDefaultH1Primary,
    surface = DarkColorPalette.surfaceBackground2,
    onSurface = DarkColorPalette.textDefaultH1Primary,
//    surfaceVariant = DarkColorPalette.surfaceBlocks,
//    onSurfaceVariant = DarkColorPalette.textDefaultH1Secondary,
    outline = DarkColorPalette.strokeDefault,
)

private val LightMaterialColorScheme = lightColorScheme(
    primary = LightColorPalette.surfacePrimary,
    onPrimary = LightColorPalette.textDefaultButton,
    secondary = LightColorPalette.surfaceSecondary,
    onSecondary = LightColorPalette.textDefaultBodyPrimary,
    error = LightColorPalette.textErrorDefault,
    onError = Color.White,
    background = LightColorPalette.surfaceBackground1,
    onBackground = LightColorPalette.textDefaultH1Primary,
    surface = LightColorPalette.surfaceBackground2,
    onSurface = LightColorPalette.textDefaultH1Primary,
//    surfaceVariant = LightColorPalette.surfaceBlocks,
//    onSurfaceVariant = LightColorPalette.textDefaultH1Secondary,
    outline = LightColorPalette.strokeDefault,
)


object AppTheme {
    val colors: AppColorPalette
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypographyPalette
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current!!
}

@Composable
fun AppTheme(
    darkTheme: Boolean = false,//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val colorScheme = if (darkTheme) DarkMaterialColorScheme else LightMaterialColorScheme
    val typography = createAppTypographyPalette()

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}