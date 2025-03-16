package org.white.green.profile.ui.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.white.green.designSystem.ui.dialogs.DatePickerBottomSheet
import org.white.green.designSystem.ui.duel.DateInfoView
import org.white.green.designSystem.ui.duel.LeftTitleRightRoundedValue
import org.white.green.designSystem.ui.single_ui_view.AppButton
import org.white.green.designSystem.ui.single_ui_view.AppDropDown
import org.white.green.designSystem.ui.single_ui_view.AppInputField
import org.white.green.designSystem.ui.single_ui_view.StrokeBox
import org.white.green.developer.countries
import org.white.green.developer.degrees
import org.white.green.developer.indianStates
import org.white.green.utils.appDateTime.AppDateTime
import org.white.green.utils.ext.AppUnitType
import org.white.green.utils.ext.formatWithUnit
import spacing

@Composable
fun PreferencesForm(
    viewModel: PreferencesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val buttonUIState by viewModel.stateUI.collectAsState()
    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var height by remember { mutableStateOf(170) } // Default height value


    Column(modifier = Modifier.padding(spacing.small).verticalScroll(scrollState)) {

        StrokeBox {
            DateInfoView(
                "Age",
                AppDateTime.formatDate(selectedDate),
                AppDateTime.formatAge(selectedDate)
            ) {
                showDatePicker = true
            }
        }


        if (showDatePicker) {
            DatePickerBottomSheet(onDismiss = {
                showDatePicker = false
            }) { date ->
                selectedDate = date
                showDatePicker = false
                viewModel.handleIntent(PreferencesIntent.UpdateAgeRange(selectedDate))
            }
        }

        Spacer(Modifier.height(spacing.large))

        // Height Range
        PreferenceSlider(
            label = "Height",
            unit = AppUnitType.Feet, // Assuming AppUnitType.CM exists
            value = height,
            valueRange = 150..220
        ) { newHeight ->
            print("new $newHeight")
            height = newHeight
            viewModel.handleIntent(PreferencesIntent.UpdateHeightRange(newHeight))
        }

        Spacer(Modifier.height(spacing.large))

        AppInputField("Village/City", value = state.preferences.location) {
            viewModel.handleIntent(PreferencesIntent.UpdateLocation(it))
        }

        Spacer(Modifier.height(spacing.large))

        AppInputField("Gotra", value = state.preferences.gotra) {
            viewModel.handleIntent(PreferencesIntent.UpdateGotra(it))
        }

        AppDropDown(
            label = "Preferred Complexion",
            selected = state.preferences.complexion,
            options = listOf("Fair", "Wheatish", "Dusky", "Dark", "Very Fair"),
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateComplexion((it))) }
        )
        Spacer(Modifier.height(spacing.large))
        AppDropDown(
            label = "Highest Education",
            selected = state.preferences.education,
            options = degrees,
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateEducation(it)) }
        )
        Spacer(Modifier.height(spacing.large))
        AppDropDown(
            label = "State",
            selected = state.preferences.state,
            options = indianStates,
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateState(it)) }
        )
        Spacer(Modifier.height(spacing.large))
        AppDropDown(
            label = "Current Country",
            selected = state.preferences.country,
            options = countries,
            onOptionSelected = { viewModel.handleIntent(PreferencesIntent.UpdateCountry(it)) }
        )

        Spacer(Modifier.height(spacing.large))
        // Submit Button

        AppButton(
            text = buttonUIState.title,
            enabled = state.isSubmitEnabled,
            buttonType = buttonUIState.type,
            modifier = Modifier.fillMaxWidth().padding(top = spacing.large),
            onClick = {
                viewModel.handleIntent(PreferencesIntent.SubmitPreferences(viewModel.state.value.preferences))
            }
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceSlider(
    label: String,
    unit: AppUnitType,
    value: Int,
    valueRange: IntRange,
    onValueChange: (Int) -> Unit
) {
    var sliderValue by remember { mutableStateOf(value.toFloat()) } // Track slider state

    StrokeBox {
        Column {
            LeftTitleRightRoundedValue(label, "${sliderValue.toInt().formatWithUnit(unit)} (${unit.title})")

            Slider(
                value = sliderValue,
                onValueChange = { newValue ->
                    sliderValue = newValue // Update slider state
                    onValueChange(newValue.toInt()) // Notify parent about the change
                },
                valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
                steps = valueRange.last - valueRange.first - 1,
                modifier = Modifier.padding(vertical = spacing.medium)
            )
        }
    }
}
