package org.white.green

import kotlinx.serialization.Serializable


@Serializable
sealed class AppRoute(val route: String) {
    @Serializable
    data object SignIn : AppRoute("sign_in")
    @Serializable
    data object LogInRoute : AppRoute("log_in")
    @Serializable
    data object ForgotPassword : AppRoute("forgot_password")
    @Serializable
    data object ProfileBasicInfo : AppRoute("profile_basic_info")
    @Serializable
    data object Chat : AppRoute("chat")
    @Serializable
    data object MainProfileDashboard : AppRoute("main_profile_dashboard")
    @Serializable
    data object ChangeProfilePic : AppRoute("change_profile_pic")
    @Serializable
    data object ChangeProfileBasicInfo : AppRoute("change_profile_basic_info")
    @Serializable
    data object ProfilePreferences : AppRoute("preferences")
    @Serializable
    data object ProfileEditFamily : AppRoute("edit_family")
    @Serializable
    data object ProfileEditInfo : AppRoute("basic_profile_info")
    @Serializable
    data object ColorUI : AppRoute("color_ui")
    @Serializable
    data object MatchUI : AppRoute("match_ui")

    companion object {
        fun getRouteTitle(route: String): String {
            return when (route) {
                SignIn.route -> "Sign In"
                LogInRoute.route -> "Log In"
                ForgotPassword.route -> "Forgot Password"
                ProfileBasicInfo.route -> "Profile"
                Chat.route -> "Chat"
                MainProfileDashboard.route -> "Dashboard"
                ChangeProfilePic.route -> "Change Profile Picture"
                ChangeProfileBasicInfo.route -> "Edit Profile Info"
                ProfilePreferences.route -> "Preferences"
                ProfileEditFamily.route -> "Edit Family Information"
                ProfileEditInfo.route -> "Edit Profile"
                ColorUI.route -> "Colors"
                MatchUI.route -> "Match"
                else -> "Unknown"
            }
        }
    }
}
