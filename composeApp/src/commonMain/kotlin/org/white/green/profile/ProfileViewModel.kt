package org.white.green.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.white.green.firestore.ProfileAuth
import org.white.green.profile.data.ProfileModel
import org.white.green.profile.ui.preferences.PreferencesModel
import org.white.green.profile.ui.preferences.PreferencesRepository

class ProfileViewModel : ViewModel() {

    private val _profileState = MutableStateFlow<UIState<ProfileModel>>(UIState.Loading)
    val profileState: StateFlow<UIState<ProfileModel>> = _profileState.asStateFlow()

    private val _preferencesState = MutableStateFlow<UIState<PreferencesModel>>(UIState.Loading)
    val preferencesState: StateFlow<UIState<PreferencesModel>> = _preferencesState.asStateFlow()


    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.FetchProfile -> fetchProfile()
            is ProfileIntent.SaveProfile -> saveProfile(intent.profile)
            is ProfileIntent.FetchPreferences -> fetchPreferences()
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = UIState.Loading
            try {
                val profileData = ProfileAuth.fetchProfileFromFirestore()
                _profileState.value = UIState.Success(profileData)
            } catch (e: Exception) {
                _profileState.value = UIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchPreferences() {
        viewModelScope.launch {
            try {
                val profileData = PreferencesRepository().fetchPreferences()
                _preferencesState.value = UIState.Success(profileData.getOrThrow())
            } catch (e: Exception) {
                println(e.message)
                _preferencesState.value = UIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun saveProfile(profile: ProfileModel) {
        viewModelScope.launch {
            try {
                ProfileAuth.saveProfile(
                    firstName = profile.firstName,
                    lastName = profile.lastName,
                    email = profile.email,
                    phoneNumber = profile.phoneNumber
                )

            } catch (e: Exception) {
                _profileState.value = UIState.Error(e.message ?: "Save failed")
            }
        }
    }

    // Profile Intent
    sealed class ProfileIntent {
        data object FetchProfile : ProfileIntent()
        data class SaveProfile(val profile: ProfileModel) : ProfileIntent()
        data object FetchPreferences : ProfileIntent()
    }
}


sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}

