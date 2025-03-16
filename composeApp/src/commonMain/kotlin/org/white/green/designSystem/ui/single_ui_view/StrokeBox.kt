package org.white.green.designSystem.ui.single_ui_view


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import spacing

@Composable
fun StrokeBox(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = spacing.dimen1,
    strokeColor: Color = MaterialTheme.colorScheme.secondary,
    cornerShape: Shape = MaterialTheme.shapes.extraSmall,
    contentPadding: Dp = spacing.medium,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .border(strokeWidth, strokeColor, shape = cornerShape)
            .padding(contentPadding)
    ) {
        content()
    }
}
