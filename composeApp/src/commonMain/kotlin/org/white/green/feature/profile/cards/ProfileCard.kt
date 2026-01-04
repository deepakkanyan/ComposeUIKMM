package org.white.green.feature.profile.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.white.green.designSystem.ui.duel.LeftRightInfoView
import org.white.green.designSystem.ui.duel.TitleWithRightIcon
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.designSystem.ui.helper.noRippleClickable
import org.white.green.designSystem.ui.ui_state.ErrorUI
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.feature.profile.ui.basicProfile.BasicProfileModel
import spacing

@Composable
fun ProfileCard(profileState: UIState<BasicProfileModel>, onClick: () -> Unit, onRetry: () -> Unit={}) {

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
            GlobalCardView {
                Column(
                    modifier = Modifier.noRippleClickable {
                        onClick.invoke()
                    },
                    horizontalAlignment = Alignment.Start
                ) {
                    TitleWithRightIcon(profile.name){ onClick.invoke()}
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Email", profile.email)
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Phone", profile.phone)
                    Spacer(modifier = Modifier.height(spacing.large))
                    LeftRightInfoView("Bio", profile.bio)
                }
            }
        }

        is UIState.Error -> {
            ErrorUI({ Text("Profile Information") },profileState.error, onRetry = onRetry, onAction = onClick)
        }
    }

}
