package org.ahivs.features.shifts.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ahivs.shared.base.utils.LocationFetchError
import org.ahivs.shared.base.utils.LocationFetchSuccess
import org.ahivs.shared.base.utils.LocationFetcher
import javax.inject.Inject

class ShiftActionViewModel @Inject constructor(
    private val shiftActionRepo: ShiftActionRepo,
    private val locationFetcher: LocationFetcher
) :
    ViewModel() {

    fun getLocation() {
        viewModelScope.launch {
            val locationInfo = locationFetcher.fetchCurrentLocation()
            when (locationInfo) {
                is LocationFetchSuccess -> println(
                    "${locationInfo.latitude} :: ${locationInfo.longitude}"
                )
                is LocationFetchError -> println("ERROR +$locationInfo.errorMsg}")
            }
        }
    }

}