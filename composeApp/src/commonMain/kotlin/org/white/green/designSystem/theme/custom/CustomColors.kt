package org.white.green.designSystem.theme.custom

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Custom Colors for Light/Dark Mode
val lightEmptyState = Color(0xFF000000)
val darkEmptyState = Color(0xFFFFFFFF)
val greenColor = Color(0xFF00C853)

// Custom Color Scheme
@Immutable
data class ExtendedColorScheme(
    val emptyStateColor: Color,
    val success: Color
)

// Composition Local for Extended Colors
val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColorScheme(emptyStateColor = Color.Unspecified, success = Color.Unspecified)
}
