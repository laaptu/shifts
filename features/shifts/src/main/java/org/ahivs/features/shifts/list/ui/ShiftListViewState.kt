package org.ahivs.features.shifts.list.ui

import org.ahivs.features.shifts.data.Shift

sealed class ShiftListViewState
object EmptyStateShift : ShiftListViewState()
object ProgressStateShift : ShiftListViewState()
class LoadedStateWithAddShift(val shifts: List<Shift>) : ShiftListViewState()
class LoadedStateWithRemove(val shifts: List<Shift>, val shiftToBeDeleted: Shift) :
    ShiftListViewState()