package org.white.green.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.white.green.designSystem.ui.ui_state.UIState
import org.white.green.designSystem.ui.ui_state.handleException
import org.white.green.profile.ui.basicProfile.BasicProfileModel
import org.white.green.profile.ui.basicProfile.BasicProfileRepository
import org.white.green.profile.ui.family.FamilyInfoModel
import org.white.green.profile.ui.family.FamilyRepository
import org.white.green.profile.ui.personal.PersonalModel
import org.white.green.profile.ui.personal.PersonalRepository

class ProfileViewModel(
    private val personalRepo: PersonalRepository,
    private val familyRepo: FamilyRepository,
    private val profileRepo: BasicProfileRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<UIState<BasicProfileModel>>(UIState.Loading)
    val profileState: StateFlow<UIState<BasicProfileModel>> = _profileState.asStateFlow()

    private val _preferencesState = MutableStateFlow<UIState<PersonalModel>>(UIState.Loading)
    val preferencesState: StateFlow<UIState<PersonalModel>> = _preferencesState.asStateFlow()

    private val _familyState = MutableStateFlow<UIState<FamilyInfoModel>>(UIState.Loading)
    val familyState: StateFlow<UIState<FamilyInfoModel>> = _familyState.asStateFlow()


    init {
        fetchProfile()
        fetchPreferences()
        fetchFamily()
    }

     fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = UIState.Loading
            try {
                val profileData = profileRepo.fetch()
                _profileState.value = UIState.Success(profileData.getOrThrow())
            } catch (e: Exception) {
                _profileState.value = handleException(e)
            }
        }
    }

    fun fetchPreferences() {
        viewModelScope.launch {
            try {
                val profileData = personalRepo.fetchPreferences()
                _preferencesState.value = UIState.Success(profileData.getOrThrow())
            } catch (e: Exception) {
                _preferencesState.value = handleException(e)
            }
        }
    }

    fun fetchFamily() {
        viewModelScope.launch {
            try {
                val profileData = familyRepo.fetch()
                _familyState.value = UIState.Success(profileData.getOrThrow())
            } catch (e: Exception) {
                _familyState.value = handleException(e)
            }
        }
    }
}