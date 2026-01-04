package org.white.green.designSystem.ui.single_ui_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerImage(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier, // Allow passing additional modifiers
    size: Dp = 70.dp,
    cornerRadius: Dp = 8.dp,
    contentScale: ContentScale = ContentScale.Crop // Default content scale
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier // Apply external modifiers first
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
    )
}