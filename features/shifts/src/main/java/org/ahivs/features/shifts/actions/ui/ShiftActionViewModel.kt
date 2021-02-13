package org.ahivs.features.shifts.actions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ahivs.features.shifts.actions.domain.Ignore
import org.ahivs.features.shifts.actions.domain.ShiftActionRepo
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse
import org.ahivs.features.shifts.actions.domain.ShiftDataProvider
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.shared.base.utils.LocationFetchError
import org.ahivs.shared.base.utils.LocationFetchSuccess
import org.ahivs.shared.base.utils.LocationFetcher
import org.ahivs.shared.base.utils.Logger
import javax.inject.Inject

class ShiftActionViewModel @Inject constructor(
    private val shiftActionRepo: ShiftActionRepo,
    private val shiftDataProvider: ShiftDataProvider,
    private val locationFetcher: LocationFetcher,
    private val logger: Logger,
    viewStateProvider: ShiftActionStateProvider
) :
    ViewModel() {

    companion object {
        private val TAG: String = ShiftActionViewModel::class.java.simpleName
        private const val DUMMY_LOCATION = "0.000"
    }

    private var prevViewState: ShiftActionViewState = viewStateProvider.getInitialShiftActionState()

    private val _viewState: MutableLiveData<ShiftActionViewState> = MutableLiveData(prevViewState)
    val viewState: LiveData<ShiftActionViewState> = _viewState

    private var enableLocationFetch: Boolean = false

    private val _shiftResponse: MutableLiveData<ShiftActionResponse> = MutableLiveData(Ignore)
    val shiftResponse: LiveData<ShiftActionResponse> = _shiftResponse

    fun init(startTime: String? = null, enableLocationFetch: Boolean = false) {
        this.enableLocationFetch = enableLocationFetch
        logger.debug(TAG, "Enable location fetch =$enableLocationFetch")
        if (_viewState.value == Progress || (prevViewState != None && _viewState.value == prevViewState))
            return
        prevViewState = if (startTime == null) {
            StartShift(shiftDataProvider.getCurrentTime())
        } else {
            EndShift(startTime)
        }
        _viewState.value = prevViewState
    }

    fun invokeShiftStartEndAction() {
        if (_viewState.value == None || _viewState.value == Progress)
            return
        viewModelScope.launch {
            _viewState.value = Progress
            val (latitude, longitude) = getLocation(enableLocationFetch)
            val shiftData: ShiftData = shiftDataProvider.createShiftData(latitude, longitude)
            val shiftActionResponse = if (prevViewState is StartShift) {
                shiftActionRepo.startShift(shiftData)
            } else {
                shiftActionRepo.endShift(shiftData)
            }
            _viewState.value = prevViewState
            _shiftResponse.value = shiftActionResponse
        }
    }

    fun clearResources() {
        _shiftResponse.value = Ignore
    }

    private suspend fun getLocation(enableLocationFetch: Boolean): Pair<String, String> {
        if (!enableLocationFetch)
            return Pair(DUMMY_LOCATION, DUMMY_LOCATION)
        val locationInfo = locationFetcher.fetchCurrentLocation()
        return when (locationInfo) {
            is LocationFetchSuccess -> Pair(
                locationInfo.latitude.toString(),
                locationInfo.longitude.toString()
            )
            is LocationFetchError -> Pair(DUMMY_LOCATION, DUMMY_LOCATION)
        }
    }


}