package org.white.green.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.white.green.AppRoute
import org.white.green.feature.profile.cards.PreferencesCard
import org.white.green.feature.profile.cards.ProfessionCard
import org.white.green.feature.profile.cards.ProfileCard
import org.white.green.feature.profile.ui.profileHeader.ProfileHeader
import spacing

@Composable
fun ProfileUI(
    viewModel: ProfileViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit
) {
    //States
    val profileState by viewModel.profileState.collectAsState()
    val preferencesState by viewModel.preferencesState.collectAsState()
    val familyState by viewModel.familyState.collectAsState()


    val scope = rememberCoroutineScope()

//    val singleImagePicker = rememberImagePickerLauncher(
//        selectionMode = SelectionMode.Single,
//        scope = scope,
//        onResult = { byteArrays ->
//            byteArrays.firstOrNull()?.let { byteArrayImage ->
//             viewModel.saveImageToFirebase(byteArrayImage)
//            }
//        }
//    )

    val scrollState = rememberScrollState()

    // Refresh when returning
    LaunchedEffect(navController.currentBackStackEntry) {
        viewModel.fetchFamily()
        viewModel.fetchPreferences()
    }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = spacing.large)) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            ProfileHeader(profileState){
             //  singleImagePicker.launch()
            }
            Spacer(Modifier.padding(vertical = spacing.medium))
            ProfileCard(profileState, onClick = {
                navController.navigate(AppRoute.ProfileEditInfo.route)
            }, onRetry = {

            })
            Spacer(Modifier.padding(spacing.medium))
            PreferencesCard(
                preferencesState = preferencesState,
                onClick = { navController.navigate(AppRoute.ProfilePreferences.route) },
                onRetry = { viewModel.fetchPreferences() }
            )
            Spacer(Modifier.padding(spacing.medium))
            ProfessionCard(
                familyState,
                onRetry = {
                    viewModel.fetchFamily()
                }, onClick = {
                    navController.navigate(AppRoute.ProfileEditFamily.route)
                })

            TextButton(
                onClick = {
                    onLogout.invoke()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = spacing.medium)
            ) {
                Text("Logout")
            }

            Text(
                "Made with ❤️",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth().padding(spacing.small)
            )

        }
    }
}
