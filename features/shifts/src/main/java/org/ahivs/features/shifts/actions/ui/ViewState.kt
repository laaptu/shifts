package org.ahivs.features.shifts.actions.ui

import javax.inject.Inject

sealed class ViewState
class StartShift(val time: String) : ViewState()
class EndShift(val startTime: String) : ViewState()
object Progress : ViewState()
object None : ViewState()

class ViewStateProvider @Inject constructor() {
    fun getInitialShiftActionState(): ViewState = None
}