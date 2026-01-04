package org.white.green.feature.profile.data


data class ProfileModel(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
) {
    val fullName: String
        get() = listOf(firstName, lastName).filter { it.isNotBlank() }.joinToString(" ")

}