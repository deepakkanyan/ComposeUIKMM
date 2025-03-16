package org.white.green.designSystem.theme.custom

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,

    val dimenPoint5: Dp = (0.5).dp,
    val dimen1: Dp = 1.dp,
    val dimen32: Dp = 32.dp
)

// Default Spacing Values
val DefaultSpacing = Spacing()

// Composition Local for Spacing
val LocalSpacing = compositionLocalOf { DefaultSpacing }

