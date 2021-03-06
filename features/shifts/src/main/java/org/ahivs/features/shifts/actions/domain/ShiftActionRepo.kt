package org.ahivs.features.shifts.actions.domain

import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SUCCESS_SHIFT_END
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SUCCESS_SHIFT_START
import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.shared.base.utils.Logger
import javax.inject.Inject

class ShiftActionRepo @Inject constructor(
    private val shiftApiService: ShiftApiService,
    private val logger: Logger
) {

    companion object {
        private val TAG: String = ShiftActionRepo::class.java.simpleName
    }

    suspend fun startShift(shiftData: ShiftData): ShiftActionResponse {
        return try {
            val response = shiftApiService.startShift(shiftData)
            logger.debug(TAG, "Shift start response = $response")
            if (response.contains(SUCCESS_SHIFT_START))
                Success
            else
                Failure(response)
        } catch (exception: Exception) {
            val errorMsg = exception.message ?: "Error starting shift"
            logger.error(TAG, errorMsg)
            Failure(errorMsg)
        }
    }

    suspend fun endShift(shiftData: ShiftData): ShiftActionResponse {
        return try {
            val response = shiftApiService.endShift(shiftData)
            logger.debug(TAG, "Shift end response = $response")
            if (response.contains(SUCCESS_SHIFT_END))
                Success
            else
                Failure(response)
        } catch (exception: Exception) {
            val errorMsg = exception.message ?: "Error ending shift"
            logger.error(TAG, errorMsg)
            Failure(errorMsg)
        }
    }
}