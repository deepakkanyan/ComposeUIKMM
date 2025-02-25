package org.white.green

import WhiteGreenTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import org.white.green.profile.ProfileUI
import org.white.green.profile.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val appStartupViewModel = AppStartupViewModel()
    val isLoggedIn by appStartupViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(AppRoute.ProfileBasicInfo.route) {
                popUpTo(AppRoute.LogIn.route) { inclusive = true }
            }
        }
    }

    WhiteGreenTheme(false) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            bottomBar = {
                if (isLoggedIn) BottomNavigationBar(navController)
            }

        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavHost(appStartupViewModel, navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(Strings.signIn, Icons.Filled.Person, AppRoute.MainProfileDashboard.route),
        BottomNavItem(Strings.profile, Icons.Filled.Favorite, AppRoute.MainProfileDashboard.route),
        BottomNavItem(Strings.chat, Icons.Filled.Face, AppRoute.Chat.route),
        BottomNavItem(Strings.advertising, Icons.Filled.AddCircle, AppRoute.ProfileBasicInfo.route)
    )
    val currentRoute = currentRoute(navController)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background

    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = { navigateToRoute(navController, item.route) }
            )
        }
    }
}

@Composable
fun AppNavHost(appStartupViewModel: AppStartupViewModel, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.LogIn.route,
        modifier = Modifier.padding(16.dp)
    ) {
        composable(AppRoute.SignIn.route) { CreateAccountScreen(viewModel = UserViewModel()) }
        composable(AppRoute.LogIn.route) {
            LoginScreen(viewModel = LoginViewModel(), navController) {
                appStartupViewModel.observeAuthChanges()
            }
        }
        composable(AppRoute.ForgotPassword.route) {
            LoginScreen(viewModel = LoginViewModel(), navController) {
                appStartupViewModel.observeAuthChanges()
                navController.navigate(AppRoute.ProfileBasicInfo.route)
            }
        }
        composable(AppRoute.MainProfileDashboard.route) { ProfileScreen(navController) }
        composable(AppRoute.ChangeProfilePic.route) { ChangeProfilePicScreen(navController) }
        composable(AppRoute.ChangeProfileBasicInfo.route) { ChangeProfileBasicInfoScreen() }
        composable(AppRoute.Chat.route) { ChatScreen() }
        composable(AppRoute.ProfileBasicInfo.route) {
            ProfileUI(navController, ProfileViewModel()) {
                appStartupViewModel.logout()
                navController.navigate(AppRoute.LogIn.route)
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text("Profile Page")
        Button(onClick = { navController.navigate(AppRoute.ChangeProfilePic.route) }) {
            Text("Change Profile Pic")
        }
    }
}

@Composable
fun ChangeProfilePicScreen(navController: NavHostController) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Change Profile Picture")
        Button(onClick = { navController.navigate(AppRoute.SignIn.route) }) {
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
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showContent = !showContent }) { Text("Click me!") }
        AnimatedVisibility(showContent) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose UI")
            }
        }
    }
}

@Stable
data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@Composable
fun currentRoute(navController: NavHostController): String? {
    return navController.currentBackStackEntryAsState().value?.destination?.route
}

fun navigateToRoute(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

object Strings {
    const val appName = "White Green"
    const val back = "Back"
    const val signIn = "Sign In"
    const val profile = "Profile"
    const val chat = "Chat"
    const val advertising = "Advertising"
}

