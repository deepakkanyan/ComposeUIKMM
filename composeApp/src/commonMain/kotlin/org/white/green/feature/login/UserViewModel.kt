package org.white.green.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.white.green.firestore.AppAuth

class UserViewModel : ViewModel() {

    // UI State Flow (Single Source of Truth)
    private val _state = MutableStateFlow<UserState>(UserState.Idle)
    val state: StateFlow<UserState> = _state

    // Input Fields
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // Handle User Actions
    fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.EnterUsername -> updateUsername(intent.username)
            is UserIntent.EnterEmail -> updateEmail(intent.email)
            is UserIntent.EnterPassword -> updatePassword(intent.password)
            is UserIntent.SubmitRegistration -> registerUser()
        }
    }

    private fun updateUsername(newUsername: String) {
        _username.value = newUsername
        validateForm()
    }

    private fun updateEmail(newEmail: String) {
        _email.value = newEmail
        validateForm()
    }

    private fun updatePassword(newPassword: String) {
        _password.value = newPassword
        validateForm()
    }

    private fun validateForm() {
        val isValid = _username.value.isNotEmpty() && _email.value.contains("@") && _password.value.length >= 6
        _state.value = UserState.Validated(isValid)
    }

    private fun registerUser() {
        if (_state.value is UserState.Validated && !(_state.value as UserState.Validated).isValid) {
            _state.value = UserState.Error("Invalid form data. Check your inputs.")
            return
        }

        executeWithLoading {
            AppAuth.registerUser(_email.value, _password.value).collect { result ->
                result.fold(
                    onSuccess = { _state.value = UserState.Success("User registered successfully!") },
                    onFailure = { error -> _state.value = UserState.Error(error.message ?: "Registration failed.") }
                )
            }
        }
    }

    // Generic function for handling async operations
    private fun executeWithLoading(action: suspend () -> Unit) {
        viewModelScope.launch {
            _state.value = UserState.Loading
            try {
                action()
            } catch (e: Exception) {
                _state.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
}

// Intent (User Actions)
sealed class UserIntent {
    data class EnterUsername(val username: String) : UserIntent()
    data class EnterEmail(val email: String) : UserIntent()
    data class EnterPassword(val password: String) : UserIntent()
    data object SubmitRegistration : UserIntent()
}

// State (UI States)
sealed class UserState {
    data object Idle : UserState()
    data object Loading : UserState()
    data class Validated(val isValid: Boolean) : UserState()
    data class Success(val message: String) : UserState()
    data class Error(val errorMessage: String) : UserState()
}
