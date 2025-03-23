package org.white.green

import WhiteGreenTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.white.green.chat.ChatUI
import org.white.green.login.CreateAccountScreen
import org.white.green.login.LoginScreen
import org.white.green.login.UserViewModel
import org.white.green.match.MatchUI
import org.white.green.profile.ProfileUI
import org.white.green.profile.ui.basicProfile.BasicProfileForm
import org.white.green.profile.ui.family.FamilyForm
import org.white.green.profile.ui.personal.PreferencesForm
import spacing

@Composable
fun App() {
    val navController = rememberNavController()
    val appStartupViewModel = remember { AppStartupViewModel() }
    val isLoggedIn by appStartupViewModel.isLoggedIn.collectAsState()


    WhiteGreenTheme(false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            topBar = { TopNavigationBar(navController) },
            bottomBar = { if (isLoggedIn) BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavHost(appStartupViewModel, navController) {
                    // Login success
                    appStartupViewModel.observeAuthChanges()
                    navController.navigate(AppRoute.ProfileBasicInfo.route) {
                        popUpTo(AppRoute.LogInRoute.route) { inclusive = true }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (isLoggedIn) {
            delay(10)
            navController.navigate(AppRoute.ProfileBasicInfo.route) {
                popUpTo(AppRoute.LogInRoute.route) { inclusive = true }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    val bottomNavRoutes = listOf(
        AppRoute.MainProfileDashboard.route,
        AppRoute.Chat.route,
        AppRoute.ProfileBasicInfo.route,
        AppRoute.LogInRoute.route
    )
    if (currentRoute !in bottomNavRoutes) {
        TopAppBar(
            title = {
                Text(
                    AppRoute.getRouteTitle(currentRoute ?: ""),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,  // Background color
                titleContentColor = MaterialTheme.colorScheme.onPrimary, // Text color
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(Strings.chat, Icons.Filled.ChatBubble, AppRoute.Chat.route),
        BottomNavItem(Strings.match, Icons.Filled.Favorite, AppRoute.ColorUI.route),
        BottomNavItem(Strings.profile, Icons.Filled.AccountCircle, AppRoute.ProfileBasicInfo.route),
    )
    val currentRoute = currentRoute(navController)

    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
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
fun AppNavHost(
    appStartupViewModel: AppStartupViewModel,
    navController: NavHostController,
    onLogin: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.LogInRoute.route,
        modifier = Modifier.padding(spacing.large)
    ) {
        composable(AppRoute.SignIn.route) { CreateAccountScreen(viewModel = UserViewModel()) }
        composable(AppRoute.LogInRoute.route) {
            LoginScreen(navController) {
                onLogin.invoke()
            }
        }
        composable(AppRoute.ForgotPassword.route) {
            LoginScreen(navController) {
                onLogin.invoke()
                navController.navigate(AppRoute.ProfileBasicInfo.route)
            }
        }
        composable(AppRoute.Chat.route) { ChatUI() }
        profileComposable(appStartupViewModel, navController)
        composable(AppRoute.ColorUI.route) { MatchUI() }
    }
}

fun NavGraphBuilder.profileComposable(
    appStartupViewModel: AppStartupViewModel,
    navController: NavHostController
) {
    composable(AppRoute.ProfileBasicInfo.route) {
        ProfileUI(navController = navController) {
            appStartupViewModel.logout()
            navController.navigate(AppRoute.LogInRoute.route)
        }
    }
    composable(AppRoute.ProfilePreferences.route) {
        PreferencesForm()
    }
    composable(AppRoute.ProfileEditFamily.route) {
        FamilyForm()
    }
    composable(AppRoute.ProfileEditInfo.route) {
        BasicProfileForm()
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
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

object Strings {
    const val dashboard = "Dashboard"
    const val profile = "Profile"
    const val chat = "Chat"
    const val match = "Match"
}
