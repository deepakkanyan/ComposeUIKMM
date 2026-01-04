package org.white.green.feature.profile.ui.family

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.koin.compose.viewmodel.koinViewModel
import org.white.green.designSystem.ui.single_ui_view.AppButton
import org.white.green.designSystem.ui.single_ui_view.AppDropDown
import spacing

@Composable
fun FamilyForm(viewModel: FamilyViewModel = koinViewModel()) {

    val familyInfo by viewModel.familyInfo.collectAsState()
    val buttonUIState by viewModel.stateUI.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.large)
    ) {
        // Father's Name Input
        InputField("Father's Name", familyInfo.fatherName) {
            viewModel.updateFamilyInfo(familyInfo.copy(fatherName = it))
        }

        // Father's Occupation Dropdown
        AppDropDown(
            label = "Father's Occupation",
            selected = familyInfo.fatherOccupation,
            options = listOf("Private", "Govt", "Business", "Farmer", "Other"),
            onOptionSelected = { selected ->
                viewModel.updateFamilyInfo(
                    familyInfo.copy(
                        fatherOccupation = selected
                    )
                )
            }
        )

        Spacer(Modifier.padding(spacing.medium))

        // Mother's Name Input
        InputField("Mother's Name", familyInfo.motherName) {
            viewModel.updateFamilyInfo(familyInfo.copy(motherName = it))
        }

        // Mother's Occupation Dropdown
        AppDropDown(
            label = "Mother's Occupation",
            selected = familyInfo.motherOccupation,
            options = listOf("Private", "Housewife", "Govt", "Business", "Farmer", "Other"),
            onOptionSelected = { selected ->
                viewModel.updateFamilyInfo(familyInfo.copy(motherOccupation = selected))
            }
        )
        Spacer(Modifier.padding(spacing.medium))
        // Sister Count Control
        CounterField(
            label = "Sisters",
            count = familyInfo.sistersCount,
            onIncrease = { viewModel.increaseSisters() },
            onDecrease = { viewModel.decreaseSisters() }
        )

        // Brother Count Control
        CounterField(
            label = "Brothers",
            count = familyInfo.brothersCount,
            onIncrease = { viewModel.increaseBrothers() },
            onDecrease = { viewModel.decreaseBrothers() }
        )

        Spacer(modifier = Modifier.height(spacing.extraLarge))

        // Save Button

        AppButton(
            text = buttonUIState.title,
            enabled = familyInfo.isSubmitEnabled,
            buttonType = buttonUIState.type,
            modifier = Modifier.fillMaxWidth().padding(top = spacing.large),
            onClick = {
                viewModel.saveToFirebase()
            }
        )
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.labelLarge) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(spacing.medium))
}

@Composable
fun CounterField(label: String, count: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)

        Row( verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease, enabled = count > 0) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease $label")
            }
            Text(text = count.toString(), modifier = Modifier.padding(horizontal = spacing.large))
            IconButton(onClick = onIncrease) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase $label")
            }
        }
    }
}
