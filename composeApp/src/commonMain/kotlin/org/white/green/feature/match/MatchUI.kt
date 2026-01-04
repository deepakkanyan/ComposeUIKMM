package org.white.green.feature.match

import MatchViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.feature.match.component.ProfileItem
import org.white.green.feature.match.repo.CompleteProfile
import spacing

@Composable
fun MatchUI(viewModel: MatchViewModel = koinViewModel(),) {
    // Collect profiles as State
    val profiles by viewModel.profiles.collectAsState()

    // Load more when reaching the end
    LaunchedEffect(profiles) {
        snapshotFlow { profiles.size }
            .collectLatest { size ->
                if (size > 0) {
                    viewModel.loadNextPage() // Fetch next batch
                }
            }
    }

    MatchList(profiles)

}

@Composable
fun MatchList(profiles: List<CompleteProfile>) {

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(profiles) { profile ->
                ProfileItem(profile)
            }
        }
        GlobalCardView(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = spacing.small).height(50.dp).width(100.dp),
            shape = RoundedCornerShape(spacing.extraLarge),
            contentPadding = spacing.small,
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.Center)) {
                Text("Filter", modifier = Modifier.padding()
                 , style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.padding(start = spacing.small))
                Icon(Icons.Sharp.FilterList, "")
            }

        }
    }

}


