package org.white.green.login
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.white.green.base.BaseViewModel
import org.white.green.firestore.AppAuth

class LoginViewModel : BaseViewModel() {

    // UI State Flow (single source of truth)
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state


    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    // Handle User Actions
    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> validateEmail(intent.email)
            is LoginIntent.EnterPassword -> validatePassword(intent.password)
            is LoginIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            is LoginIntent.SubmitLogin -> login()
            is LoginIntent.CheckLoginStatus -> isLoggedIn()
        }
    }

    private fun validateEmail(newEmail: String) {
        _email.value = newEmail
        validateForm()
    }

    private fun validatePassword(newPassword: String) {
        _password.value = newPassword
        validateForm()
    }

    private fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    private fun validateForm() {
        val isValid = _email.value.contains("@") && _password.value.length >= 6
        _state.value = LoginState.Validated(isValid)
    }

    private fun login() {
        executeWithLoading {
            AppAuth.login(_email.value, _password.value).collect { result ->
                  result.fold(onSuccess = {
                      _state.value =  LoginState.Success("Login successful! Welcome, $result")
                  }, onFailure = {
                      _state.value =  LoginState.Error("$result")
                  })

            }
        }
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            delay(1000)
            println("ss ${Firebase.auth.currentUser} ")
            AppAuth.isUserLoggedIn().collect { result ->
                println("SpK ${result}")
                _state.value = LoginState.IsLoggedIn(result)
            }
        }
    }

    // Generic function for handling async operations
    private fun executeWithLoading(action: suspend () -> Unit) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                action()
            } catch (e: Exception) {
                _state.value = LoginState.Error("Error: ${e.message}")
            }
        }
    }
}

// Intent (User Actions)
sealed class LoginIntent {
    data class EnterEmail(val email: String) : LoginIntent()
    data class EnterPassword(val password: String) : LoginIntent()
    data object TogglePasswordVisibility : LoginIntent()
    data object SubmitLogin : LoginIntent()
    data object CheckLoginStatus : LoginIntent()
}

// State (UI States)
sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class IsLoggedIn(val isLoggedIn: Boolean) : LoginState()
    data class Validated(val isValid: Boolean) : LoginState()
    data class Success(val message: String) : LoginState()
    data class Error(val errorMessage: String) : LoginState()
}

