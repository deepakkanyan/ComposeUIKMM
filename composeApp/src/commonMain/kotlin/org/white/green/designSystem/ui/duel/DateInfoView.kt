package org.white.green.designSystem.ui.duel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.white.green.designSystem.ui.helper.noRippleClickable
import spacing


@Preview
@Composable
fun DateInfoView(label: String, title: String, subTitle: String, onDateChange: () -> Unit = {}) {

    Column(modifier = Modifier
        .noRippleClickable { onDateChange.invoke() }) {
        LeftTitleRightRoundedValue(label, subTitle)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = spacing.extraSmall)
        ) {

            if (title.isEmpty()) {
                Text(
                    "Select $label",
                    style = MaterialTheme.typography.labelLarge,
                )

            } else {
                Text(title, style = MaterialTheme.typography.labelLarge)

            }
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Rounded.ChevronRight,
                contentDescription = "",
                modifier = Modifier.size(spacing.large).alpha(0.6f),
                tint = MaterialTheme.colorScheme.secondary,
            )
        }

    }

}