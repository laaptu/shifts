package org.ahivs.features.shifts.list.ui

import org.ahivs.features.shifts.data.Shift
import javax.inject.Inject

sealed class ListViewState
object EmptyState : ListViewState()
object ProgressState : ListViewState()
class LoadedStateWithStart(val shifts: List<Shift>) : ListViewState()
class LoadedStateWithEnd(val shifts: List<Shift>, val shiftToBeEnded: Shift) :
    ListViewState()


class ListViewStateProvider @Inject constructor() {
    fun getInitialShiftListViewState(): ListViewState = EmptyState
}