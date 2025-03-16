package org.white.green.utils.appDateTime

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object AppDateTime {

    // Format: 12 Mar 2024
    fun formatDate(selectedDateMillis: Long?): String {

        if(selectedDateMillis == null){
            return  ""
        }

        val localDate = Instant.fromEpochMilliseconds(selectedDateMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        return "${localDate.dayOfMonth} ${localDate.month.name.take(3)} ${localDate.year}"
    }

    // Format: 26 Y (Calculates age)
    fun formatAge(selectedDateMillis: Long?): String {
        if(selectedDateMillis == null){
            return  ""
        }
        val birthDate = Instant.fromEpochMilliseconds(selectedDateMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val currentDate = kotlinx.datetime.Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val age = currentDate.year - birthDate.year
        return "$age Y"
    }
}
