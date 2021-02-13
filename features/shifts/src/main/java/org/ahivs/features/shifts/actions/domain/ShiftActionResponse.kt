package org.ahivs.features.shifts.actions.domain

sealed class ShiftActionResponse
object Success : ShiftActionResponse()
class Failure(val errorMsg: String) : ShiftActionResponse()

//this doesn't have any importance on domain state for now
object Ignore : ShiftActionResponse()