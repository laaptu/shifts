package org.ahivs.features.shifts.actions

import org.ahivs.features.shifts.ShiftApiService
import org.ahivs.features.shifts.ShiftData
import javax.inject.Inject

class ShiftActionRepo @Inject constructor(private val shiftApiService: ShiftApiService) {
    suspend fun startShift(shiftData: ShiftData): String = shiftApiService.startShift(shiftData)
    suspend fun endShift(shiftData: ShiftData): String = shiftApiService.endShift(shiftData)
}