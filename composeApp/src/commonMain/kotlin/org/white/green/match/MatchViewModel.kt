import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.white.green.base.BaseViewModel
import org.white.green.match.repo.CompleteProfile
import org.white.green.match.repo.MatchRepository

class MatchViewModel : BaseViewModel() {

    private val repository = MatchRepository()

    private val _profiles = MutableStateFlow<List<CompleteProfile>>(emptyList())
    val profiles: StateFlow<List<CompleteProfile>> = _profiles

    fun loadNextPage(limit: Int = 1) {
        viewModelScope.launch {
            repository.fetchProfiles(limit).onSuccess { newProfiles ->
                // **Avoid duplicates by merging with existing data**
                val uniqueProfiles = (_profiles.value + newProfiles).distinctBy { it.userProfile.email }
                _profiles.value = uniqueProfiles
                println("MatchViewModel fetched ${newProfiles.size} profiles, total: ${_profiles.value.size}")
            }.onFailure {
                println("MatchViewModel Failed: ${it.message}")
            }
        }
    }

    init {
        loadNextPage() // Load first batch
    }
}
