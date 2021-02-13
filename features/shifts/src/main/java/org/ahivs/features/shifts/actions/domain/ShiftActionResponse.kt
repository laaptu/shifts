package org.ahivs.features.shifts.actions.domain

sealed class ShiftActionResponse {
    companion object {
        const val SUCCESS_SHIFT_START = "Start shift - All good"
        const val SHIFT_IN_PROGRESS = "Nope, shift already in progress"
        const val SUCCESS_SHIFT_END = "End shift - All good"
        const val SHIFT_NOT_STARTED = "Nope, no shift started to be closed"
    }
}

object Success : ShiftActionResponse()
class Failure(val errorMsg: String) : ShiftActionResponse()