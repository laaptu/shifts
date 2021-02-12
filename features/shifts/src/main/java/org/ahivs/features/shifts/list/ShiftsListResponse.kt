package org.ahivs.features.shifts.list

import org.ahivs.features.shifts.data.Shift

sealed class ShiftsListResponse
class FetchSuccess(val shifts: List<Shift>) : ShiftsListResponse()
class FetchError(val errorMsg: String, val exception: Exception) : ShiftsListResponse()