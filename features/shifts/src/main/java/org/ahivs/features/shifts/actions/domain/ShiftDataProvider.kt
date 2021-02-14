package org.ahivs.features.shifts.actions.domain

import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.features.shifts.utils.DateUtils
import javax.inject.Inject

class ShiftDataProvider @Inject constructor() {

    companion object {
        const val DUMMY_LOCATION = "0.0000"
    }

    fun createShiftData(
        longitude: String? = null,
        latitude: String? = null,
        time: String = getCurrentTime(),
    ): ShiftData =
        ShiftData(
            time,
            latitude ?: DUMMY_LOCATION,
            longitude ?: DUMMY_LOCATION
        )

    fun getCurrentTime(): String = DateUtils.getCurrentTime()

}