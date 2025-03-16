package org.white.green.designSystem.ui.duel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import org.white.green.designSystem.ui.helper.noRippleClickable
import spacing

@Composable
fun TitleWithRightIcon(
    title: String,
    leftIcon: ImageVector? = null,
    icon: ImageVector = Icons.Rounded.ChevronRight,
    showDividerInBottom: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().noRippleClickable { onClick.invoke() }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leftIcon != null) {
                    Icon(
                        leftIcon,
                        contentDescription = "edit",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(spacing.dimen32)
                            .padding(end = spacing.extraSmall)
                    )
                    Spacer(Modifier.padding(spacing.extraSmall))
                }
                Text(title, style = MaterialTheme.typography.bodyLarge)
            }

            Icon(
                icon,
                contentDescription = "edit",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.alpha(0.5f)
            )
        }

        if(showDividerInBottom){
        Spacer(Modifier.padding(top = spacing.small))
        HorizontalDivider(thickness = spacing.dimenPoint5)
        }

    }

}
