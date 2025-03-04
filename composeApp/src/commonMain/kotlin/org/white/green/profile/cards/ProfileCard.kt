package org.white.green.profile.cards

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
import org.white.green.profile.ProfileRow
import org.white.green.profile.UIState
import org.white.green.profile.data.ProfileModel

@Composable
fun ProfileCard(profileState: UIState<ProfileModel>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        when (profileState) {
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
                val profile = profileState.data
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(profile.fullName)
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "edit",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    ProfileRow("Email", profile.email)
                    Spacer(modifier = Modifier.height(22.dp))
                    ProfileRow("Phone", profile.phoneNumber)
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
                    Text("Error loading profile", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
