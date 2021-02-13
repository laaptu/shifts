package org.ahivs.features.shifts.list.ui

import org.ahivs.features.shifts.data.Shift

sealed class ShiftAction
object Start : ShiftAction()
class End(val shiftToBeEnded: Shift) : ShiftAction()