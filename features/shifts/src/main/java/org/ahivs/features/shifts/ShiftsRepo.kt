package org.ahivs.features.shifts

class ShiftsRepo(private val shiftApiService: ShiftApiService) {
    suspend fun getShifts(): List<Shift> = shiftApiService.getShifts()
}