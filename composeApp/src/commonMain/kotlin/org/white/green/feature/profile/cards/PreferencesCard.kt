package org.white.green.feature.profile.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.white.green.designSystem.ui.duel.LeftRightInfoView
import org.white.green.designSystem.ui.duel.TitleWithRightIcon
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.designSystem.ui.helper.noRippleClickable
import org.white.green.designSystem.ui.ui_state.ErrorUI
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.feature.profile.ui.personal.PersonalModel
import org.white.green.utils.appDateTime.AppDateTime
import spacing

@Composable
fun PreferencesCard(
    preferencesState: UIState<PersonalModel>,
    onRetry: () -> Unit,
    onClick: () -> Unit
) {

    when (preferencesState) {
        is UIState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(spacing.extraSmall))
            }
        }

        is UIState.Success -> {
            val preferences = preferencesState.data
            GlobalCardView {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().noRippleClickable { onClick() },
                    horizontalAlignment = Alignment.Start
                ) {
                    TitleWithRightIcon(leftIcon = Icons.Default.AccountCircle, title = "Bio Data"){ onClick.invoke()}
                    Spacer(modifier = Modifier.height(spacing.extraLarge))
                    LeftRightInfoView("Gotra", preferences.gotra)
                    Spacer(modifier = Modifier.height(spacing.medium))
                    LeftRightInfoView(
                        "Village (State)",
                        "${preferences.location} (${preferences.state})"
                    )
                    Spacer(modifier = Modifier.height(spacing.medium))
                    LeftRightInfoView(
                        "Age",
                        AppDateTime.formatAge(preferences.ageRange.dobTimeMiles)
                    )
                    Spacer(modifier = Modifier.height(spacing.medium))
                    LeftRightInfoView("Height", preferences.heightRange.getHeight())
                    Spacer(modifier = Modifier.height(spacing.medium))
                    LeftRightInfoView("Current Country", preferences.country)
                    Spacer(modifier = Modifier.height(spacing.medium))
                }
            }
        }

        is UIState.Error -> {

            ErrorUI({
                TitleWithRightIcon(
                    leftIcon = Icons.Default.AccountCircle,
                    title = "Bio Data"
                ){
                    onClick.invoke()
                }
            }, preferencesState.error, onRetry = onRetry)
        }
    }
}
