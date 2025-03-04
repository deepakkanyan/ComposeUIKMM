package org.white.green.utils.ext

import org.white.green.profile.ui.preferences.AppUnitType

fun Int.toFeetAndInches(): String {
    val totalInches = (this / 2.54).toInt() // Convert cm to inches
    val feet = totalInches / 12
    val inches = totalInches % 12
    return "$feet,$inches'"
}


fun Int.formatWithUnit(unit: AppUnitType): String {
    return when (unit) {
        AppUnitType.Years -> "$this"
        AppUnitType.Feet -> this.toFeetAndInches()
    }
}
