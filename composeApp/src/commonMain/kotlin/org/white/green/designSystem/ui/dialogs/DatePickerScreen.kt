package org.white.green.designSystem.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    onDismiss: () -> Unit = {},
    onDateSelected: (Long?) -> Unit
) {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val eighteenYearsAgo = now.year - 18
    val initialDateMillis = Clock.System.now()
        .minus(value = 18, unit = DateTimeUnit.YEAR, timeZone = TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        yearRange = 1950..eighteenYearsAgo,
        initialDisplayMode = DisplayMode.Input,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
                .padding(spacing.large)
        ) {
            DatePicker(
                state, colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background

                )
            )
            Button(
                onClick = {
                    onDateSelected(state.selectedDateMillis)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().padding(top = spacing.large)
            ) {
                Text("Confirm Date")
            }
        }
    }

}
