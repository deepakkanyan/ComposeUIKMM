package org.white.green.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable


@Composable
fun AppNavHost(navController: NavController = rememberNavController()) {

    Scaffold { padding ->
//        NavHost(
//            navController = navController,
//            startDestination = LoginScreen(LoginViewModel()),
//            modifier = Modifier.padding(padding)
//        ) {
//
//
//        }
    }

}