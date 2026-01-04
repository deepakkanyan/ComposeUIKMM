package org.white.green.feature.profile.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.white.green.designSystem.ui.single_ui_view.AppButtonType
import org.white.green.designSystem.ui.single_ui_view.ButtonState

class PreferencesViewModel(private val repo: PersonalRepository) : ViewModel() {

    private val _state = MutableStateFlow(PreferencesState())
    val state: StateFlow<PreferencesState> = _state.asStateFlow()

    private val _stateUI = MutableStateFlow(ButtonState("Save Preferences", AppButtonType.Primary))
    val stateUI: StateFlow<ButtonState> = _stateUI.asStateFlow()

    init {
        fetchPersonalInfo()
    }

    private fun fetchPersonalInfo() {
        viewModelScope.launch {
            repo.fetchPreferences().onSuccess {
                _state.value = PreferencesState(preferences = it)
            }
        }

    }

    fun handleIntent(intent: PreferencesIntent) {
        viewModelScope.launch {
            when (intent) {
                is PreferencesIntent.UpdateAgeRange -> updatePreferences {
                    it.copy(ageRange = AgeRange(intent.dob))
                }

                is PreferencesIntent.UpdateHeightRange -> updatePreferences {
                    it.copy(heightRange = HeightRange(intent.height))
                }

                is PreferencesIntent.UpdateComplexion -> updatePreferences {
                    it.copy(complexion = intent.complexions)
                }

                is PreferencesIntent.UpdateEducation -> updatePreferences {
                    it.copy(education = intent.education)
                }

                is PreferencesIntent.UpdateGotra -> updatePreferences {
                    it.copy(gotra = intent.gotra)
                }

                is PreferencesIntent.UpdateLocation -> updatePreferences {
                    it.copy(location = intent.location)
                }

                is PreferencesIntent.UpdateState -> updatePreferences {
                    it.copy(state = intent.state)
                }

                is PreferencesIntent.UpdateCountry -> updatePreferences {
                    it.copy(country = intent.country)
                }

                is PreferencesIntent.SubmitPreferences -> {
                    submitToDatabase(_state.value.preferences)
                }
            }
        }
    }

    private fun updatePreferences(update: (PersonalModel) -> PersonalModel) {
        _state.update { currentState ->
            currentState.copy(preferences = update(currentState.preferences))
        }
    }


    private fun submitToDatabase(preferences: PersonalModel) {
        _stateUI.update {
            ButtonState("Saving...", AppButtonType.Primary)
        }
        viewModelScope.launch {
            val repo = PersonalRepository().savePreferences(preferences)
            repo.onSuccess {
                println("Preferences saved successfully ")
                _stateUI.update {
                    ButtonState("Saved", AppButtonType.Success)
                }
            }.onFailure { _ ->
                _stateUI.update {
                    ButtonState("Error", AppButtonType.Error)
                }
            }
        }

    }

}


// UI State
data class PreferencesState(
    val preferences: PersonalModel = PersonalModel(
        ageRange = AgeRange(0),
        heightRange = HeightRange(0),
        complexion = "",
        education = "",
        gotra = "",
        location = "",
        state = "",
        country = ""
    )
) {
    val isSubmitEnabled: Boolean
        get() = preferences.complexion.isNotBlank() &&
                preferences.education.isNotBlank() &&
                preferences.gotra.isNotBlank() &&
                preferences.location.isNotBlank() &&
                preferences.state.isNotBlank() &&
                preferences.country.isNotBlank()
}


// User Actions (Intent)
sealed class PreferencesIntent {
    data class UpdateAgeRange(val dob: Long?) : PreferencesIntent()
    data class UpdateHeightRange(val height: Int) : PreferencesIntent()
    data class UpdateComplexion(val complexions: String) : PreferencesIntent()
    data class UpdateEducation(val education: String) : PreferencesIntent()
    data class UpdateGotra(val gotra: String) : PreferencesIntent()
    data class UpdateLocation(val location: String) : PreferencesIntent()
    data class UpdateState(val state: String) : PreferencesIntent()
    data class UpdateCountry(val country: String) : PreferencesIntent()
    data class SubmitPreferences(val model: PersonalModel) : PreferencesIntent()
}
