package org.ahivs.features.shifts.data

/*
*
* */

data class ShiftData(val time: String, val latitude: String, val longitude: String)

data class Shift(
    val id: Int, val start: String, val end: String, val startLatitude: String,
    val startLongitude: String, val endLatitude: String, val endLongitude: String,
    val image: String
)