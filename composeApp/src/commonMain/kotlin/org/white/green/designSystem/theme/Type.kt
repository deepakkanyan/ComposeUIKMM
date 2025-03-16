package org.white.green.designSystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.lato_bold
import kotlinproject.composeapp.generated.resources.lato_light
import kotlinproject.composeapp.generated.resources.lato_regular
import org.jetbrains.compose.resources.Font


@Composable
fun AppFontFamily() = FontFamily(
    Font(Res.font.lato_light, weight = FontWeight.Normal),
    Font(Res.font.lato_regular, weight = FontWeight.Medium),
    Font(Res.font.lato_bold, weight = FontWeight.Bold)
)

@Composable
fun AppTypography() = Typography(
    displayLarge = TextStyle(fontSize = 57.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    displayMedium = TextStyle(fontSize = 45.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    displaySmall = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    headlineLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    headlineMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    headlineSmall = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    titleSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    bodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily()),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium, fontFamily = AppFontFamily())
)
