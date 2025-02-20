package org.white.green.login
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isValid = MutableStateFlow(false)
    val isValid: StateFlow<Boolean> = _isValid

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        validate()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        validate()
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    private fun validate() {
        _isValid.value = _email.value.contains("@") && _password.value.length >= 6
    }

    fun login() {
        viewModelScope.launch {

        }
    }
}