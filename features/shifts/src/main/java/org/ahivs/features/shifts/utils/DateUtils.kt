package org.ahivs.features.shifts.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        private const val ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        private val serverDateFormat = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
        private val uiDateFormat = SimpleDateFormat("EEE,d MMM yyyy 'at' hh:mm:ss", Locale.US)

        @JvmStatic
        fun getCurrentTime(): String {
            return serverDateFormat.format(Date())
        }

        @JvmStatic
        fun getUIDateFormat(orgDate: String): String {
            return try {
                val date = serverDateFormat.parse(orgDate)
                uiDateFormat.format(date)
            } catch (exception: Exception) {
                orgDate
            }
        }
    }
}