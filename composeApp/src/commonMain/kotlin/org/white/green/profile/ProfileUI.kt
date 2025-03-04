package org.white.green.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.white.green.profile.cards.PreferencesCard
import org.white.green.profile.cards.ProfileCard

@Composable
fun ProfileUI(navController: NavController, onLogout: () -> Unit) {
    val viewModel: ProfileViewModel = remember { ProfileViewModel() }

    //States
    val profileState by viewModel.profileState.collectAsState()
    val preferencesState by viewModel.preferencesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(ProfileViewModel.ProfileIntent.FetchProfile)
        viewModel.processIntent(ProfileViewModel.ProfileIntent.FetchPreferences)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ProfileCard(profileState)
            Spacer(Modifier.padding(10.dp))
            PreferencesCard(
                preferencesState = preferencesState,
                navController = navController,
                onRetry = { viewModel.processIntent(ProfileViewModel.ProfileIntent.FetchPreferences) }
            )

            TextButton(
                onClick = {
                    viewModel.processIntent(ProfileViewModel.ProfileIntent.FetchPreferences)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

        }
    }
}

/** Profile Row */
@Composable
fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Text(text = value, color = MaterialTheme.colorScheme.primary)
    }
}
