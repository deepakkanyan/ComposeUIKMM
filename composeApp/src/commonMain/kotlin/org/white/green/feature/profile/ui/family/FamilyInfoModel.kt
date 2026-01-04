package org.white.green.feature.profile.ui.family

import kotlinx.serialization.Serializable

@Serializable
data class FamilyInfoModel(
    val fatherName: String = "",
    val fatherOccupation: String = "",
    val motherName: String = "",
    val motherOccupation: String = "",
    val sistersCount: Int = 0,
    val brothersCount: Int = 0
){
    val isSubmitEnabled: Boolean
        get() = fatherName.isNotBlank() &&
                fatherOccupation.isNotBlank() &&
                motherName.isNotBlank() &&
                motherOccupation.isNotBlank()
}
