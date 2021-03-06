package org.ahivs.features.shifts.data

import org.ahivs.features.shifts.utils.DateUtils

/*
*
* */

data class ShiftData(val time: String, val latitude: String, val longitude: String)

data class Shift(
    val id: Int, val start: String, val end: String, val startLatitude: String,
    val startLongitude: String, val endLatitude: String, val endLongitude: String,
    val image: String
) {
    fun getStartLocation(): String = startLatitude.plus("/").plus(startLongitude)
    fun getEndLocation(): String = endLatitude.plus("/").plus(endLongitude)
    fun getStartTime(): String = DateUtils.getUIDateFormat(start)
    fun getEndTime(): String = DateUtils.getUIDateFormat(end)
}
