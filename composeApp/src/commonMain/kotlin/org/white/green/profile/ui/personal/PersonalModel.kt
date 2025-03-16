package org.white.green.profile.ui.personal

import kotlinx.serialization.Serializable
import org.white.green.utils.ext.toFeetAndInches

@Serializable
data class PersonalModel(
    val ageRange: AgeRange,
    val heightRange: HeightRange,
    val complexion: String,
    val education: String,
    val gotra: String,
    val location: String,
    val state: String,
    val country: String,
)

@Serializable
data class AgeRange(val dobTimeMiles : Long?)

@Serializable
data class HeightRange(val height:Int) {
     fun getHeight() = "${height.toFeetAndInches()} Ft."
}
