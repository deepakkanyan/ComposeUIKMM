package org.white.green.profile.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.white.green.AppRoute
import org.white.green.profile.ProfileRow
import org.white.green.profile.UIState
import org.white.green.profile.ui.preferences.PreferencesModel

@Composable
fun PreferencesCard(
    preferencesState: UIState<PreferencesModel>,
    navController: NavController,
    onRetry: () -> Unit
) {
    Card (
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        when (preferencesState) {
            is UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val preferences = preferencesState.data
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(AppRoute.ProfilePreferences.route) }
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Preferences")
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "edit",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    ProfileRow("Country", preferences.preferredLocation)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileRow("Religion", preferences.preferredReligion)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileRow("Age", preferences.preferredAgeRange.age())
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileRow("Height", preferences.preferredHeightRange.height())
                }
            }

            is UIState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Error loading preferences", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}
