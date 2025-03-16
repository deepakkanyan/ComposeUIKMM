package org.white.green.designSystem.ui.helper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null, // No ripple effect
        interactionSource = remember { MutableInteractionSource() }, // No touch feedback
        onClick = onClick
    )
}
