package org.ahivs.features.shifts.actions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ahivs.features.shifts.actions.domain.ShiftActionRepo
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse
import org.ahivs.features.shifts.actions.domain.ShiftDataProvider
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.shared.base.utils.LocationFetchError
import org.ahivs.shared.base.utils.LocationFetchSuccess
import org.ahivs.shared.base.utils.LocationFetcher
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.base.utils.event.Event
import javax.inject.Inject

class ShiftActionViewModel @Inject constructor(
    private val shiftActionRepo: ShiftActionRepo,
    private val shiftDataProvider: ShiftDataProvider,
    private val locationFetcher: LocationFetcher,
    private val logger: Logger,
    viewStateProvider: ViewStateProvider
) :
    ViewModel() {

    companion object {
        private val TAG: String = ShiftActionViewModel::class.java.simpleName
    }

    private var prevViewState: ViewState = viewStateProvider.getInitialShiftActionState()

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(prevViewState)
    val viewState: LiveData<ViewState> = _viewState

    private var enableLocationFetch: Boolean = false

    private val _shiftResponse: MutableLiveData<Event<ShiftActionResponse>> = MutableLiveData()
    val shiftResponse: LiveData<Event<ShiftActionResponse>> = _shiftResponse

    fun init(startTime: String? = null, enableLocationFetch: Boolean = false) {
        this.enableLocationFetch = enableLocationFetch
        logger.debug(TAG, "Enable location fetch =$enableLocationFetch")
        if (_viewState.value == Progress || (prevViewState != None && _viewState.value == prevViewState))
            return
        prevViewState = if (startTime == null) {
            StartShift(shiftDataProvider.getCurrentTime())
        } else {
            EndShift(shiftDataProvider.getCurrentTime())
        }
        _viewState.value = prevViewState
    }

    fun invokeShiftStartEndAction() {
        if (_viewState.value == None || _viewState.value == Progress)
            return
        viewModelScope.launch {
            _viewState.value = Progress
            val shiftData = createShiftData()
            val shiftActionResponse = if (prevViewState is StartShift) {
                shiftActionRepo.startShift(shiftData)
            } else {
                shiftActionRepo.endShift(shiftData)
            }
            _viewState.value = prevViewState
            _shiftResponse.value = Event(shiftActionResponse)
        }
    }

    private suspend fun createShiftData(): ShiftData {
        return if (enableLocationFetch) {
            val (latitude, longitude) = getLocation()
            shiftDataProvider.createShiftData(latitude, longitude)
        } else {
            shiftDataProvider.createShiftData()
        }
    }

    private suspend fun getLocation(): Pair<String?, String?> {
        val locationInfo = locationFetcher.fetchCurrentLocation()
        return when (locationInfo) {
            is LocationFetchSuccess -> Pair(
                locationInfo.latitude.toString(),
                locationInfo.longitude.toString()
            )
            is LocationFetchError -> Pair(null, null)
        }
    }


}