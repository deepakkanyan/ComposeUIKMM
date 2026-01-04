package org.white.green.designSystem.ui.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import spacing

@Composable
fun GlobalCardView(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(spacing.extraSmall),
    elevation: Dp = 0.5.dp,
    contentPadding: Dp = spacing.large,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentAlignment = contentAlignment
        ) {
            content()
        }
    }
}
