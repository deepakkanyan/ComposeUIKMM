package org.white.green.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.white.green.firestore.ProfileAuth
import org.white.green.profile.data.ProfileModel

class ProfileViewModel : ViewModel() {

    // Profile State
    sealed class ProfileState {
        data object Loading : ProfileState()
        data class Success(val profile: ProfileModel) : ProfileState()
        data class Error(val message: String) : ProfileState()
    }

    // Profile Intent
    sealed class ProfileIntent {
        data object FetchProfile : ProfileIntent()
        data class SaveProfile(val profile: ProfileModel) : ProfileIntent()
    }

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.FetchProfile -> fetchProfile()
            is ProfileIntent.SaveProfile -> saveProfile(intent.profile)
        }
    }

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val profileData = ProfileAuth.fetchProfileFromFirestore()
                _state.value = ProfileState.Success(profileData)
            } catch (e: Exception) {
                _state.value = ProfileState.Error(e.message ?: "Unknown error")
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
                fetchProfile() // Refresh after saving
            } catch (e: Exception) {
                _state.value = ProfileState.Error(e.message ?: "Save failed")
            }
        }
    }
}
