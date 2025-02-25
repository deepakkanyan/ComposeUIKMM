package org.white.green.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.white.green.AppRoute

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController, onLogIn: () -> Unit) {
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
        if (state is LoginState.IsLoggedIn) {
            val loginState = state as LoginState.IsLoggedIn // Explicit casting
            if (loginState.isLoggedIn) {
                navController.navigate(AppRoute.ProfileBasicInfo) {
                    popUpTo(AppRoute.LogIn) {
                        inclusive = true
                    }
                }
            }
        }else if (state is LoginState.Success){
            onLogIn()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.handleIntent(LoginIntent.EnterEmail(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.handleIntent(LoginIntent.SubmitLogin) },
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {

        }) {

            Text("Forgot Password?")
        }

        TextButton(onClick = { navController.navigate(AppRoute.SignIn) }) {
            Text("Create Account")
        }

        // Show login state messages
        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Success -> Text(
                (state as LoginState.Success).message,
                color = MaterialTheme.colorScheme.primary
            )

            is LoginState.Error -> Text(
                (state as LoginState.Error).errorMessage,
                color = MaterialTheme.colorScheme.error
            )

            else -> {} // No action for Idle and Validated states
        }
    }
}
