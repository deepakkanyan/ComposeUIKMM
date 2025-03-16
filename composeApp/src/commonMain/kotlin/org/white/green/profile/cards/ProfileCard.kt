package org.white.green.profile.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.white.green.designSystem.ui.duel.LeftRightInfoView
import org.white.green.designSystem.ui.duel.TitleWithRightIcon
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.designSystem.ui.helper.noRippleClickable
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.profile.ui.basicProfile.BasicProfileModel
import spacing

@Composable
fun ProfileCard(profileState: UIState<BasicProfileModel>, onClick: () -> Unit) {
    GlobalCardView {
        when (profileState) {
            is UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                val profile = profileState.data
                Column(
                    modifier = Modifier.noRippleClickable {
                        onClick.invoke()
                    },
                    horizontalAlignment = Alignment.Start
                ) {
                    TitleWithRightIcon(profile.name)
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Email", profile.email)
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Phone", profile.phone)
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Bio", profile.bio)
                }
            }

            is UIState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Error loading profile", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
