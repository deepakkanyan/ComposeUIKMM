package org.white.green

import WhiteGreenTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.jetbrains.compose.resources.painterResource
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.white.green.login.CreateAccountScreen
import org.white.green.login.LoginScreen
import org.white.green.login.LoginViewModel
import org.white.green.login.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    WhiteGreenTheme(true){
        Scaffold(
            topBar = { TopAppBar(title = { Text("White Green") }) },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Sign In", Icons.Filled.Person, AppRoutes.SignIn.name),
        BottomNavItem("Profile", Icons.Filled.Favorite, AppRoutes.MainProfileDashboard.name),
        BottomNavItem("Chat", Icons.Filled.Face, AppRoutes.Chat.name),
        BottomNavItem("Advertising", Icons.Filled.AddCircle, AppRoutes.Chat.name)
    )

    val currentRoute = currentRoute(navController)

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.LogIn.name,
        modifier = Modifier.padding(16.dp)
    ) {
        composable(AppRoutes.SignIn.name) {
            CreateAccountScreen(viewModel = UserViewModel())
        }
        composable(AppRoutes.LogIn.name) {
            LoginScreen(viewModel = LoginViewModel(), navController)
        }
        composable(AppRoutes.ForgotPassword.name) {
            LoginScreen(viewModel = LoginViewModel(), navController)
        }
        composable(AppRoutes.MainProfileDashboard.name) { ProfileScreen(navController) }
        composable(AppRoutes.ChangeProfilePic.name) { ChangeProfilePicScreen(navController) }
        composable(AppRoutes.ChangeProfileBasicInfo.name) { ChangeProfileBasicInfoScreen() }
        composable(AppRoutes.Chat.name) { ChatScreen() }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text("Profile Page")
        Button(onClick = { navController.navigate(AppRoutes.ChangeProfilePic.name) }) {
            Text("Change Profile Pic")
        }
    }
}

@Composable
fun ChangeProfilePicScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Change Profile Picture")
        Button(onClick = { navController.navigate(AppRoutes.SignIn.name) }) {
            Text("Save Profile Pic")
        }
    }
}

@Composable
fun ChangeProfileBasicInfoScreen() {
    Text("Profile Basic Info")
}

@Composable
fun ChatScreen() {
    var showContent by remember { mutableStateOf(true) }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose UI")
            }
        }
    }
}

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
