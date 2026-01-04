package org.white.green.feature.match.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_demo
import org.jetbrains.compose.resources.painterResource
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.designSystem.ui.single_ui_view.RoundedCornerImage
import org.white.green.feature.match.repo.CompleteProfile
import org.white.green.utils.appDateTime.AppDateTime
import org.white.green.utils.ext.toFeetAndInches
import spacing

@Composable
fun ProfileItem(profile: CompleteProfile) {
    GlobalCardView(
        Modifier.padding(bottom = spacing.small)
            .background(MaterialTheme.colorScheme.background),
        contentPadding = spacing.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(horizontal = spacing.medium),
                verticalAlignment = Alignment.Top

            ) {
                RoundedCornerImage(
                    painter = painterResource(Res.drawable.ic_demo),
                )
                Column(modifier = Modifier.padding(horizontal = spacing.large)) {
                    Text(
                        profile.userProfile.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "${profile.bio.location}, ${profile.bio.state}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary

                    )

                    Spacer(modifier = Modifier.padding(top = spacing.extraSmall))

                    Row(modifier = Modifier.padding(top = spacing.extraSmall)) {
                        Text(
                            AppDateTime.formatAge(profile.bio.ageRange.dobTimeMiles),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.padding(spacing.small))
                        Text(
                            profile.bio.heightRange.height.toFeetAndInches(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.secondary

                        )

                    }
                    Text(
                        profile.bio.education,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )


                }

            }


            Row(
                modifier = Modifier.padding( top = spacing.large, end = spacing.medium).align(
                    Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Icon(
                    Icons.Sharp.FavoriteBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )

            }
        }
    }
}