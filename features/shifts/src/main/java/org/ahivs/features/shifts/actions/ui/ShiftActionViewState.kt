package org.ahivs.features.shifts.actions.ui

import javax.inject.Inject

sealed class ShiftActionViewState
class StartShift(val time: String) : ShiftActionViewState()
class EndShift(val startTime: String) : ShiftActionViewState()
object Progress : ShiftActionViewState()
object None : ShiftActionViewState()

class ShiftActionStateProvider @Inject constructor() {
    fun getInitialShiftActionState(): ShiftActionViewState = None
}