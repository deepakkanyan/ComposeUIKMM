package org.white.green.feature.profile.ui.profileHeader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.white.green.designSystem.ui.global.RoundedAsyncImage
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.feature.profile.ui.basicProfile.BasicProfileModel
import spacing

@Composable
fun ProfileHeader(
    profileState: UIState<BasicProfileModel>,
    onChangeProfile: () -> Unit
) {
    // Gradient background for the row
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .shadow(1.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(
                    vertical = spacing.dimen32,
                    horizontal = spacing.extraLarge
                ), // Balanced padding
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Greeting column
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.large), // Consistent spacing
                modifier = Modifier.weight(1f) // Take available space
            ) {
                // Handle profile state safely
                val state = profileState
                if (state is UIState.Success) {
                    Text(
                        text = "Hey, ${state.data.name}!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                }
                Text(
                    text = "☀\uFE0F Good morning!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Profile image with circular clip and shadow
            RoundedAsyncImage(
                "https://picsum.photos/id/1/200/300",
                size = spacing.dimen80,
                onClick = onChangeProfile
            )

        }


}