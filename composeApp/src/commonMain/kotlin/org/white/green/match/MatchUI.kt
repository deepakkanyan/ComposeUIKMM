package org.white.green.match

import MatchViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import org.white.green.designSystem.ui.global.GlobalCardView
import org.white.green.match.repo.CompleteProfile
import org.white.green.utils.ext.toFeetAndInches

@Composable
fun MatchUI() {
    val viewModel: MatchViewModel = viewModel()

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

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(profiles) { profile ->
                ProfileItem(profile)
            }
        }
    }
}

@Composable
fun ProfileItem(profile: CompleteProfile) {
    GlobalCardView(modifier = Modifier.padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(6.dp)) {
            Text("👤 Name: ${profile.userProfile.name}", style = MaterialTheme.typography.bodyLarge)
            Text("📍 Location: ${profile.bio.location}, ${profile.bio.state}", style = MaterialTheme.typography.bodyMedium)
            Text("🎓 Education: ${profile.bio.education}", style = MaterialTheme.typography.bodyMedium)
            Text("📏 Height: ${profile.bio.heightRange.height.toFeetAndInches()}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
