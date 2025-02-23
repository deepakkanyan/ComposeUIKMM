package org.white.green.login
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CreateAccountScreen(viewModel: UserViewModel) {
    val uiState by viewModel.state.collectAsState()
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.handleIntent(UserIntent.EnterUsername(it)) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.handleIntent(UserIntent.EnterEmail(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.handleIntent(UserIntent.EnterPassword(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.handleIntent(UserIntent.SubmitRegistration) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState is UserState.Validated && (uiState as UserState.Validated).isValid
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is UserState.Loading -> CircularProgressIndicator()
            is UserState.Success -> Text(
                text = (uiState as UserState.Success).message,
                color = MaterialTheme.colorScheme.primary
            )
            is UserState.Error -> Text(
                text = (uiState as UserState.Error).errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            else -> {} // Do nothing for Idle or Validated states
        }
    }
}
