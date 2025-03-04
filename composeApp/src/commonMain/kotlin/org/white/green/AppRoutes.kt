package org.white.green

sealed class AppRoute(val route: String) {
    data object SignIn : AppRoute("sign_in")
    data object LogIn : AppRoute("log_in")
    data object ForgotPassword : AppRoute("forgot_password")
    data object ProfileBasicInfo : AppRoute("profile_basic_info")
    data object Chat : AppRoute("chat")
    data object MainProfileDashboard : AppRoute("main_profile_dashboard")
    data object ChangeProfilePic : AppRoute("change_profile_pic")
    data object ChangeProfileBasicInfo : AppRoute("change_profile_basic_info")
    data object ProfilePreferences : AppRoute("preferences")
    data object ProfileEditProfile : AppRoute("edit_profile")

    companion object {
        fun getRouteTitle(route: String): String {
            return when (route) {
                SignIn.route -> "Sign In"
                LogIn.route -> "Log In"
                ForgotPassword.route -> "Forgot Password"
                ProfileBasicInfo.route -> "Profile"
                Chat.route -> "Chat"
                MainProfileDashboard.route -> "Dashboard"
                ChangeProfilePic.route -> "Change Profile Picture"
                ChangeProfileBasicInfo.route -> "Edit Profile Info"
                ProfilePreferences.route -> "Preferences"
                ProfileEditProfile.route -> "Edit Profile"
                else -> "Unknown"
            }
        }
    }
}
