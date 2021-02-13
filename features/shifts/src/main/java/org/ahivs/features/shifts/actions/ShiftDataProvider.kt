package org.ahivs.features.shifts.actions

import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.features.shifts.utils.DateUtils
import javax.inject.Inject

class ShiftDataProvider @Inject constructor() {

    fun createShiftData(longitude: String = "0.0000", latitude: String = "0.0000"): ShiftData =
        ShiftData(DateUtils.getCurrentTime(), latitude, longitude)

}