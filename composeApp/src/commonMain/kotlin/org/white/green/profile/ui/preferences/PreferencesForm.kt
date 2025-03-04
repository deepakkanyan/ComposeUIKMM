package org.white.green.profile.ui.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.white.green.utils.ext.formatWithUnit

@Composable
fun PreferencesForm(
    viewModel: PreferencesViewModel = viewModel(),
    onSubmit: (PreferencesModel) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.padding(8.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            PreferenceRangeSlider(
                label = "Age Range",
                unit = AppUnitType.Years,
                value = state.preferences.preferredAgeRange.min to state.preferences.preferredAgeRange.max,
                valueRange = 18..50,
                onValueChange = { min, max ->
                    viewModel.handleIntent(PreferencesIntent.UpdateAgeRange(min, max))
                }
            )
        }
        Spacer(Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            // Height Range
            PreferenceRangeSlider(
                label = "Height Range",
                unit = AppUnitType.Feet,
                value = state.preferences.preferredHeightRange.min to state.preferences.preferredHeightRange.max,
                valueRange = 140..200,
                onValueChange = { min, max ->
                    viewModel.handleIntent(PreferencesIntent.UpdateHeightRange(min, max))
                }
            )
        }
        Spacer(Modifier.height(16.dp))
        // Dropdowns
        PreferenceDropdown(
            label = "Preferred Complexion",
            selected = state.preferences.preferredComplexion,
            options = listOf("Fair", "Wheatish", "Dusky", "Dark", "Very Fair"),
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateComplexion((it))) }
        )
        Spacer(Modifier.height(16.dp))
        PreferenceDropdown(
            label = "Preferred Education",
            selected = state.preferences.preferredEducation,
            options = listOf("Bachelor's", "Master's", "PhD", "Diploma", "High School"),
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateEducation(it)) }
        )
        Spacer(Modifier.height(16.dp))
        PreferenceDropdown(
            label = "Preferred Religion",
            selected = state.preferences.preferredReligion,
            options = listOf("Hindu", "Muslim", "Christian", "Sikh", "Buddhist"),
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateReligion(it)) }
        )
        Spacer(Modifier.height(16.dp))
        PreferenceDropdown(
            label = "Preferred Location",
            selected = state.preferences.preferredLocation,
            options = listOf("India", "USA", "Canada", "UK", "Australia"),
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateLocation(it)) }
        )
        Spacer(Modifier.height(22.dp))
        // Submit Button
        Button(
            enabled = state.isSubmitEnabled,
            onClick = {

                viewModel.handleIntent(PreferencesIntent.SubmitPreferences)
                println(viewModel.state.value.preferences)

            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Save Preferences")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceRangeSlider(
    label: String,
    unit: AppUnitType,
    value: Pair<Int, Int>,
    valueRange: IntRange,
    onValueChange: (Int, Int) -> Unit
) {
    Column(Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), // Make Row take full width
            horizontalArrangement = Arrangement.SpaceBetween // Push texts to edges
        ) {
            Text(
                text = label, style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${value.first.formatWithUnit(unit)}-${value.second.formatWithUnit(unit)} (${unit.title})",
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.secondary),
            )
        }
        RangeSlider(
            value = value.first.toFloat()..value.second.toFloat(),
            onValueChange = { range ->
                onValueChange(
                    range.start.toInt(),
                    range.endInclusive.toInt()
                )
            },
            valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
            steps = 20,
            modifier = Modifier.padding(vertical = 8.dp)
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryEditable, true),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        println("onCLick Drop ")
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

sealed class AppUnitType(val title: String) {
    data object Years : AppUnitType("In Years")
    data object Feet : AppUnitType("In feet")
}
