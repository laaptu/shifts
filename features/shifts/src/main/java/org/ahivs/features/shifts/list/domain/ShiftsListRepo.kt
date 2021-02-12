package org.ahivs.features.shifts.list.domain

import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.shared.base.utils.Logger
import javax.inject.Inject

class ShiftsListRepo @Inject constructor(
    private val shiftApiService: ShiftApiService,
    private val logger: Logger
) {
    companion object {
        private val TAG = ShiftsListRepo::class.java.simpleName
    }

    suspend fun getShifts(): ShiftsListResponse {
        return try {
            val shifts = shiftApiService.getShifts()
            logger.debug(TAG, "Shifts fetched successfully of size = ${shifts.size}")
            FetchSuccess(shifts)
        } catch (npt: NullPointerException) {
            //as API is returning null for empty list, it is done in this manner
            //ideally API should have returned empty list as json payload
            logger.warn(TAG, "Returning empty lists due to:\n$npt\n${npt.message}")
            FetchSuccess(emptyList())
        } catch (exception: Exception) {
            val errorMsg = exception?.message ?: "Error fetching shifts"
            logger.error(TAG, "Error fetching list due to:\n$exception\n${errorMsg}")
            FetchError(errorMsg, exception)
        }

    }
}