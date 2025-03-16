package org.white.green.designSystem.ui.single_ui_view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import extendedColors

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: AppButtonType = AppButtonType.Primary,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (buttonType) {
                AppButtonType.Primary -> MaterialTheme.colorScheme.primary
                AppButtonType.Secondary -> MaterialTheme.colorScheme.secondary
                AppButtonType.Disabled -> MaterialTheme.colorScheme.surfaceVariant
                AppButtonType.Success -> extendedColors.success
                AppButtonType.Error -> MaterialTheme.colorScheme.error
            },
            contentColor = when (buttonType) {
                AppButtonType.Primary -> MaterialTheme.colorScheme.onPrimary
                AppButtonType.Secondary -> MaterialTheme.colorScheme.onSecondary
                AppButtonType.Disabled -> MaterialTheme.colorScheme.onSurfaceVariant
                AppButtonType.Success -> MaterialTheme.colorScheme.background
                AppButtonType.Error -> MaterialTheme.colorScheme.onError
            },
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        enabled = enabled
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

enum class AppButtonType {
    Primary, Secondary, Disabled, Success, Error
}

class ButtonState(var title: String, var type: AppButtonType)
