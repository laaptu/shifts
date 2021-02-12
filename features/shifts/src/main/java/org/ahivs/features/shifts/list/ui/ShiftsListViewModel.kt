package org.ahivs.features.shifts.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ahivs.features.shifts.list.domain.FetchError
import org.ahivs.features.shifts.list.domain.FetchSuccess
import org.ahivs.features.shifts.list.domain.ShiftsListRepo
import javax.inject.Inject

class ShiftsListViewModel @Inject constructor(private val shiftsListRepo: ShiftsListRepo) :
    ViewModel() {

    fun fetchShifts() {
        viewModelScope.launch {
            val shiftsListResponse = shiftsListRepo.getShifts()
            when (shiftsListResponse) {
                is FetchSuccess -> println("Fetch success of list size = ${shiftsListResponse.shifts.size}")
                is FetchError -> println("Error = ${shiftsListResponse.errorMsg}")
            }
        }
    }
}