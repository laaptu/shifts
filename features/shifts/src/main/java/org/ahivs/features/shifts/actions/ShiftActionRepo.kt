package org.ahivs.features.shifts.actions

import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.features.shifts.data.ShiftData
import javax.inject.Inject

class ShiftActionRepo @Inject constructor(private val shiftApiService: ShiftApiService) {
    suspend fun startShift(shiftData: ShiftData): String = shiftApiService.startShift(shiftData)
    suspend fun endShift(shiftData: ShiftData): String = shiftApiService.endShift(shiftData)
}