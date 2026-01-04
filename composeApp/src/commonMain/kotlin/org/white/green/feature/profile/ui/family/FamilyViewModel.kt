package org.white.green.feature.profile.ui.family

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.white.green.base.BaseViewModel
import org.white.green.designSystem.ui.single_ui_view.AppButtonType
import org.white.green.designSystem.ui.single_ui_view.ButtonState

class FamilyViewModel(private val repo: FamilyRepository) : BaseViewModel() {


    private val _familyInfo = MutableStateFlow(FamilyInfoModel())
    val familyInfo = _familyInfo.asStateFlow()

    private val _stateUI = MutableStateFlow(ButtonState("Save", AppButtonType.Primary))
    val stateUI: StateFlow<ButtonState> = _stateUI.asStateFlow()


    init {
        fetchFamilyInfo()
    }

    private fun fetchFamilyInfo() {
        viewModelScope.launch {
            repo.fetch().onSuccess {
                _familyInfo.value = it
            }
        }

    }

    fun updateFamilyInfo(update: FamilyInfoModel) {
        _familyInfo.value = update
    }

    fun increaseSisters() {
        _familyInfo.value =
            _familyInfo.value.copy(sistersCount = _familyInfo.value.sistersCount + 1)
    }

    fun decreaseSisters() {
        if (_familyInfo.value.sistersCount > 0) {
            _familyInfo.value =
                _familyInfo.value.copy(sistersCount = _familyInfo.value.sistersCount - 1)
        }
    }

    fun increaseBrothers() {
        _familyInfo.value =
            _familyInfo.value.copy(brothersCount = _familyInfo.value.brothersCount + 1)
    }

    fun decreaseBrothers() {
        if (_familyInfo.value.brothersCount > 0) {
            _familyInfo.value =
                _familyInfo.value.copy(brothersCount = _familyInfo.value.brothersCount - 1)
        }
    }

    fun saveToFirebase() {
        _stateUI.value = ButtonState("Saving...", AppButtonType.Secondary)
        viewModelScope.launch {
            repo.save(familyInfo.value).onSuccess {
                _stateUI.value = ButtonState("Saved Successfully", AppButtonType.Success)
            }.onFailure { _ ->
                _stateUI.value = ButtonState("Failed", AppButtonType.Error)
            }
        }
    }
}
