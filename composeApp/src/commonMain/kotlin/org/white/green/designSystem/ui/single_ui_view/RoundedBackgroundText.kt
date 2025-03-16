package org.white.green.designSystem.ui.single_ui_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import spacing

@Composable
fun RoundedBackgroundText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape =  MaterialTheme.shapes.medium
            )
            .padding(horizontal = spacing.small, vertical = spacing.extraSmall)
    )
}
