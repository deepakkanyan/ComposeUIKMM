package org.white.green.profile.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.white.green.designSystem.ui.duel.LeftRightInfoView
import org.white.green.designSystem.ui.duel.TitleWithRightIcon
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.designSystem.ui.helper.noRippleClickable
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.profile.ui.family.FamilyInfoModel
import spacing

@Composable
fun ProfessionCard(
    familyInfoModel: UIState<FamilyInfoModel>,
    onClick: () -> Unit,
    onRetry: () -> Unit
) {
    when (familyInfoModel) {
        is UIState.Loading -> {
            Text("Loading...")
        }

        is UIState.Error -> {
            Text("Error loading family info")
        }

        is UIState.Success -> {
            val model = familyInfoModel.data
            FamilyCard(model, onClick)
        }
    }
}

@Composable
private fun FamilyCard(model: FamilyInfoModel, onClick: () -> Unit) {
    GlobalCardView {
        Column(modifier = Modifier.noRippleClickable { onClick.invoke() }) {
            TitleWithRightIcon(leftIcon = Icons.Rounded.Groups, title = "Family")
            Spacer(modifier = Modifier.height(spacing.extraLarge))
            LeftRightInfoView("Father", model.fatherName)
            Spacer(modifier = Modifier.height(spacing.medium))
            LeftRightInfoView("Occupation", model.fatherOccupation)
            Spacer(modifier = Modifier.height(spacing.medium))
            LeftRightInfoView("Mother", model.motherName)
            Spacer(modifier = Modifier.height(spacing.medium))
            LeftRightInfoView("Occupation", model.motherOccupation)
            Spacer(modifier = Modifier.height(spacing.medium))
            LeftRightInfoView("Sister", model.sistersCount.toString())
            Spacer(modifier = Modifier.height(spacing.medium))
            LeftRightInfoView("Brother", model.brothersCount.toString())
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }
}
