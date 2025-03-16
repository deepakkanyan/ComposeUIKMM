package org.white.green.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import org.white.green.AppRoute
import spacing

@Composable
fun LoginScreen( navController: NavController, onLogIn: () -> Unit) {
    val viewModel = remember { LoginViewModel() }
    val state by viewModel.state.collectAsState()
    val email by remember { viewModel.email }.collectAsState()
    val password by remember { viewModel.password }.collectAsState()
    val isPasswordVisible by remember { viewModel.isPasswordVisible }.collectAsState()
    val isValid = remember(email, password) {
        email.contains("@") && password.length >= 6
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(LoginIntent.CheckLoginStatus)
    }

    LaunchedEffect(state) {
        if (state is LoginState.Success){
            onLogIn.invoke()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(spacing.extraLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(spacing.large))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.handleIntent(LoginIntent.EnterEmail(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.handleIntent(LoginIntent.EnterPassword(it)) },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { viewModel.handleIntent(LoginIntent.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Person else Icons.Filled.Email,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing.large))

        Button(
            onClick = { viewModel.handleIntent(LoginIntent.SubmitLogin) },
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        TextButton(onClick = {

        }) {

            Text("Forgot Password?")
        }

        TextButton(onClick = { navController.navigate(AppRoute.SignIn.route) }) {
            Text("Create Account")
        }

        // Show login state messages
        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Error -> Text(
                (state as LoginState.Error).errorMessage,
                color = MaterialTheme.colorScheme.error
            )

            else -> {} // No action for Idle and Validated states
        }
    }
}
