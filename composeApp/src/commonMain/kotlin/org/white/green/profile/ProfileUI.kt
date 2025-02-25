package org.white.green.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileUI(navController: NavController, viewModel: ProfileViewModel, onLogout: () -> Unit) {
    val profileState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (profileState) {
            is ProfileViewModel.ProfileState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ProfileViewModel.ProfileState.Success -> {
                val profile = (profileState as ProfileViewModel.ProfileState.Success).profile

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /** Profile Card */
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(profile.fullName)
                                Spacer(modifier = Modifier)
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

                    Spacer(modifier = Modifier.height(22.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp),
                        elevation = CardDefaults.cardElevation(1.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(profile.fullName)
                                Spacer(modifier = Modifier)
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

                    /** Action Buttons */
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = { onLogout() },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Logout")
                        }
                    }
                }
            }

            is ProfileViewModel.ProfileState.Error -> {
                val errorMessage = (profileState as ProfileViewModel.ProfileState.Error).message
                Text(
                    "Error: $errorMessage",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
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
        Text(text = label, fontWeight = FontWeight.W200, fontSize = 14.sp)
        Text(text = value, color = MaterialTheme.colorScheme.primary)
    }
}
