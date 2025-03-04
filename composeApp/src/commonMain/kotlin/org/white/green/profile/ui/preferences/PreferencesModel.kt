package org.white.green.profile.ui.preferences

import kotlinx.serialization.Serializable
import org.white.green.utils.ext.toFeetAndInches

@Serializable
data class PreferencesModel(
    val preferredAgeRange: AgeRange,
    val preferredHeightRange: HeightRange,
    val preferredComplexion: String,
    val preferredEducation: String,
    val preferredReligion: String,
    val preferredLocation: String,
)

@Serializable
data class AgeRange(val min: Int, val max: Int) {
    fun age() = "${min}-${max} (y)"
}

@Serializable
data class HeightRange(val min: Int, val max: Int) {
     fun height() = "${min.toFeetAndInches()}-${max.toFeetAndInches()} (Ft.)"
}
