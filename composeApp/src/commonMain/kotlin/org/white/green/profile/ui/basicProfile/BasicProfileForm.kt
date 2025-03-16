package org.white.green.profile.ui.basicProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.white.green.designSystem.ui.single_ui_view.AppButton
import org.white.green.designSystem.ui.single_ui_view.AppInputField
import spacing

@Composable
fun BasicProfileForm(viewModel: BasicProfileViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val buttonUIState by viewModel.stateUI.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(spacing.small).verticalScroll(scrollState)) {
        Spacer(Modifier.height(spacing.large))

        AppInputField("Name", value = state.name) {
            viewModel.handleIntent(BasicProfileIntent.UpdateName(it))
        }

        Spacer(Modifier.height(spacing.large))

        AppInputField("Email", value = state.email) {
            viewModel.handleIntent(BasicProfileIntent.UpdateEmail(it))
        }

        Spacer(Modifier.height(spacing.large))

        AppInputField("Phone", value = state.phone) {
            viewModel.handleIntent(BasicProfileIntent.UpdatePhone(it))
        }

        AppInputField(
            "Bio",
            hint = "Write something about yourself",
            modifier = Modifier.height(120.dp),
            value = state.bio
        ) {
            viewModel.handleIntent(BasicProfileIntent.UpdateBio(it))
        }

        Spacer(Modifier.height(spacing.large))

        Spacer(Modifier.height(spacing.large))

        // Submit Button
        AppButton(
            text = buttonUIState.title,
            enabled = state.isSubmitEnabled,
            buttonType = buttonUIState.type,
            modifier = Modifier.fillMaxWidth().padding(top = spacing.large),
            onClick = {
                viewModel.handleIntent(
                    BasicProfileIntent.SubmitContactInfo
                )
            }
        )
    }
}
