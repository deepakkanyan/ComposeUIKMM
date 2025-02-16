package org.white.green.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var successMessage by mutableStateOf("")
        private set

    fun onUsernameChange(newValue: String) {
        username = newValue
    }

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onSubmit() {
        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            successMessage = "✅ Successfully Registered!"
        } else {
            successMessage = "⚠️ Please fill all fields."
        }
    }
}
