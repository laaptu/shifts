package org.ahivs.features.shifts.list.ui

import org.ahivs.features.shifts.data.Shift
import javax.inject.Inject

sealed class ShiftListViewState
object EmptyState : ShiftListViewState()
object ProgressState : ShiftListViewState()
class LoadedStateWithStart(val shifts: List<Shift>) : ShiftListViewState()
class LoadedStateWithEnd(val shifts: List<Shift>, val shiftToBeEnded: Shift) :
    ShiftListViewState()


class ShiftListViewStateProvider @Inject constructor() {
    fun getInitialShiftListViewState(): ShiftListViewState = EmptyState
}