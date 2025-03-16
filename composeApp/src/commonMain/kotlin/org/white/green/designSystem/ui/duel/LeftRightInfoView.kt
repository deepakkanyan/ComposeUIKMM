package org.white.green.designSystem.ui.duel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import spacing

/** Profile Row */
@Composable
fun LeftRightInfoView(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = spacing.extraSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(
            text = value,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
