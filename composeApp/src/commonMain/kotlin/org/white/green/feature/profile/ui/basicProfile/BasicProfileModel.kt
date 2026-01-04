package org.white.green.feature.profile.ui.basicProfile

import kotlinx.serialization.Serializable

@Serializable
data class BasicProfileModel(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = ""
) {
    val isSubmitEnabled: Boolean
        get() = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && bio.isNotBlank()
}
