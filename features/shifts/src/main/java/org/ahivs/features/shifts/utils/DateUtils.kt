package org.ahivs.features.shifts.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private const val ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        @JvmStatic
        fun getCurrentTime(): String {
            val simpleDateFormat = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
            return simpleDateFormat.format(Date())
        }
    }
}