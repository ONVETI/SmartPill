package uz.onveti.smartpill.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.onveti.smartpill.R

@Composable
fun avenirFontFamily() = FontFamily(
    Font(R.font.avenir_next_condensed_demi_bold, FontWeight.Bold),
)

@Composable
fun robotoFontFamily() = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold),
)

@Composable
fun robotoCondensedFontFamily() = FontFamily(
    Font(R.font.roboto_condensed_regular, FontWeight.Normal),
    Font(R.font.roboto_condensed_bold, FontWeight.Bold),
)

data class AppTypographyPalette(
    val primary32Semibold: TextStyle,
    val primary28Semibold: TextStyle,
    val primary24Semibold: TextStyle,
    val primary18Semibold: TextStyle,
    val primary16Medium: TextStyle,
    val primary16Bold: TextStyle,
    val primary14SemiboldCaps: TextStyle,
    val primary14Regular: TextStyle,

    val secondary24Medium: TextStyle,
    val secondary20Regular: TextStyle,
    val secondary18Regular: TextStyle,
    val secondary18Medium: TextStyle,
    val secondary18Semibold: TextStyle,
    val secondary16Regular: TextStyle,
    val secondary16Medium: TextStyle,
    val secondary16Bold: TextStyle,
    val secondary14Regular: TextStyle,
    val secondary14Medium: TextStyle,
    val secondary12SemiboldCaps: TextStyle,

    val third16Regular: TextStyle,
    val third16Medium: TextStyle,
    val third16Bold: TextStyle,
)

@Composable
fun createAppTypographyPalette(): AppTypographyPalette {
    val avenir = avenirFontFamily()
    val roboto = robotoFontFamily()
    val robotoCondensed = robotoCondensedFontFamily()

    return AppTypographyPalette(
        primary32Semibold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 39.sp,
            letterSpacing = (-0.02).sp,
        ),
        primary28Semibold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.02).sp,
        ),
        primary24Semibold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.02).sp,
        ),
        primary18Semibold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        ),
        primary16Medium = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.02).sp,
        ),
        primary16Bold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.02).sp,
        ),
        primary14SemiboldCaps = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.01.sp,
        ),
        primary14Regular = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.01.sp,
            color = Color(0xFF808080)
        ),


        secondary20Regular = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 28.sp,
        ),
        secondary24Medium = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
        ),
        secondary18Semibold = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        ),
        secondary18Regular = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        ),
        secondary18Medium = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        ),
        secondary16Regular = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        ),
        secondary16Medium = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        ),
        secondary16Bold = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
        ),
        secondary14Regular = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
        ),
        secondary14Medium = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp,
        ),
        secondary12SemiboldCaps = TextStyle(
            fontFamily = robotoCondensed,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.02.sp,
        ),

        third16Regular = TextStyle(
            fontFamily = avenirFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.02.sp,
        ),
        third16Medium = TextStyle(
            fontFamily = avenirFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.02.sp,
        ),
        third16Bold = TextStyle(
            fontFamily = avenirFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.02.sp,
        ),
    )
}

internal val LocalAppTypography = staticCompositionLocalOf<AppTypographyPalette?> { null }
