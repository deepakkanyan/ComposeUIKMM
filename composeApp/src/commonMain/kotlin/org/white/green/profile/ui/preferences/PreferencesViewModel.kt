package org.white.green.profile.ui.preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel : ViewModel() {

    private val _state = MutableStateFlow(PreferencesState())
    val state: StateFlow<PreferencesState> = _state.asStateFlow()

    fun handleIntent(intent: PreferencesIntent, onSubmit: (PreferencesModel) -> Unit = {}) {
        viewModelScope.launch {
            when (intent) {
                is PreferencesIntent.UpdateAgeRange -> updatePreferences {
                    it.copy(preferredAgeRange = AgeRange(intent.min, intent.max))
                }
                is PreferencesIntent.UpdateHeightRange -> updatePreferences {
                    it.copy(preferredHeightRange = HeightRange(intent.min, intent.max))
                }
                is PreferencesIntent.UpdateComplexion -> updatePreferences {
                    it.copy(preferredComplexion = intent.complexions)
                }
                is PreferencesIntent.UpdateEducation -> updatePreferences {
                    it.copy(preferredEducation = intent.education)
                }
                is PreferencesIntent.UpdateReligion -> updatePreferences {
                    it.copy(preferredReligion = intent.religion)
                }
                is PreferencesIntent.UpdateLocation -> updatePreferences {
                    it.copy(preferredLocation = intent.location)
                }
                is PreferencesIntent.SubmitPreferences -> {
                    onSubmit(_state.value.preferences)
                }
            }
        }
    }

    private fun updatePreferences(update: (PreferencesModel) -> PreferencesModel) {
        _state.update { currentState ->
            currentState.copy(preferences = update(currentState.preferences))
        }
    }

}


// UI State
data class PreferencesState(
    val preferences: PreferencesModel = PreferencesModel(
        preferredAgeRange = AgeRange(25, 30),
        preferredHeightRange = HeightRange(160, 180),
        preferredComplexion = "",
        preferredEducation = "",
        preferredReligion = "",
        preferredLocation = ""
    )
) {
    val isSubmitEnabled: Boolean
        get() = preferences.preferredComplexion.isNotBlank() &&
                preferences.preferredEducation.isNotBlank() &&
                preferences.preferredReligion.isNotBlank() &&
                preferences.preferredLocation.isNotBlank()
}


// User Actions (Intent)
sealed class PreferencesIntent {
    data class UpdateAgeRange(val min: Int, val max: Int) : PreferencesIntent()
    data class UpdateHeightRange(val min: Int, val max: Int) : PreferencesIntent()
    data class UpdateComplexion(val complexions: String) : PreferencesIntent()
    data class UpdateEducation(val education: String) : PreferencesIntent()
    data class UpdateReligion(val religion: String) : PreferencesIntent()
    data class UpdateLocation(val location: String) : PreferencesIntent()
    data object SubmitPreferences : PreferencesIntent()
}
