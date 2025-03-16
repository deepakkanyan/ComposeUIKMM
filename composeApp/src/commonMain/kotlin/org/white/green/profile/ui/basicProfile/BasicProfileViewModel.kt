package org.white.green.profile.ui.basicProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.white.green.designSystem.ui.single_ui_view.AppButtonType
import org.white.green.designSystem.ui.single_ui_view.ButtonState

class BasicProfileViewModel(private val repository: BasicProfileRepository) : ViewModel() {

    private val _state = MutableStateFlow(BasicProfileModel())
    val state: StateFlow<BasicProfileModel> = _state

    private val _stateUI = MutableStateFlow(ButtonState("Save", AppButtonType.Primary))
    val stateUI: StateFlow<ButtonState> = _stateUI

    init {
        fetch()
    }

    fun handleIntent(intent: BasicProfileIntent) {
        when (intent) {
            is BasicProfileIntent.UpdateName -> updateName(intent.name)
            is BasicProfileIntent.UpdateEmail -> updateEmail(intent.email)
            is BasicProfileIntent.UpdatePhone -> updatePhone(intent.phone)
            is BasicProfileIntent.UpdateBio -> updateBio(intent.bio)
            is BasicProfileIntent.SubmitContactInfo -> submitContactInfo()
        }
    }

    private fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    private fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    private fun updatePhone(phone: String) {
        _state.value = _state.value.copy(phone = phone)
    }

    private fun updateBio(bio: String) {
        _state.value = _state.value.copy(bio = bio)
    }

    private fun fetch() {
        viewModelScope.launch {
            try {
                val profileData = repository.fetch()
                _state.value = profileData.getOrThrow()
            } catch (_: Exception) {
            }
        }
    }

    private fun submitContactInfo() {
        println(_state.value)
        _stateUI.value = ButtonState("Saving", AppButtonType.Secondary)
        viewModelScope.launch {
            repository.save(_state.value).onSuccess {
                _stateUI.value = ButtonState("Saved Successfully", AppButtonType.Success)
            }.onFailure {
                _stateUI.value = ButtonState("Error Saving", AppButtonType.Error)
            }
        }
    }
}

sealed class BasicProfileIntent {
    data class UpdateName(val name: String) : BasicProfileIntent()
    data class UpdateEmail(val email: String) : BasicProfileIntent()
    data class UpdatePhone(val phone: String) : BasicProfileIntent()
    data class UpdateBio(val bio: String) : BasicProfileIntent()
    data object SubmitContactInfo : BasicProfileIntent()
}
