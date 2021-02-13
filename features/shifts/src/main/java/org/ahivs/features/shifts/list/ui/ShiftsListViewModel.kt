package org.ahivs.features.shifts.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ahivs.features.shifts.R
import org.ahivs.features.shifts.data.Shift
import org.ahivs.features.shifts.list.domain.FetchError
import org.ahivs.features.shifts.list.domain.FetchSuccess
import org.ahivs.features.shifts.list.domain.ShiftsListRepo
import org.ahivs.shared.base.di.ComponentScope
import org.ahivs.shared.base.utils.Logger
import javax.inject.Inject

class ShiftsListViewModel @Inject constructor(
    private val shiftsListRepo: ShiftsListRepo,
    viewStateProvider: ShiftListViewStateProvider,
    private val logger: Logger
) :
    ViewModel() {

    companion object {
        private val TAG: String = ShiftsListViewModel::class.java.simpleName
    }

    private var prevViewState: ShiftListViewState = viewStateProvider.getInitialShiftListViewState()

    private val _viewState: MutableLiveData<ShiftListViewState> =
        MutableLiveData(prevViewState)
    val viewState: LiveData<ShiftListViewState> = _viewState

    private val _infoMsgData: MutableLiveData<Int> = MutableLiveData()
    val infoMsgDate: LiveData<Int> = _infoMsgData

    private val _shiftAction: MutableLiveData<ShiftAction> = MutableLiveData(None)
    val shiftAction: LiveData<ShiftAction> = _shiftAction

    fun fetchShifts() {
        if (_viewState.value != EmptyState)
            return
        refreshShifts()
    }

    fun refreshShifts() {
        viewModelScope.launch {
            if (_viewState.value == ProgressState)
                return@launch
            _viewState.value = ProgressState
            val shiftsListResponse = shiftsListRepo.getShifts()
            when (shiftsListResponse) {
                is FetchSuccess -> handleSuccess(shiftsListResponse.shifts)
                is FetchError -> {
                    _infoMsgData.value = R.string.error_fetching_shifts
                    _viewState.value = prevViewState
                }
            }
        }
    }

    fun invokeShiftStartEndAction() {
        val currViewState: ShiftListViewState? = _viewState.value
        when (currViewState) {
            is EmptyState,
            is LoadedStateWithStart -> _shiftAction.value = Start
            is LoadedStateWithEnd -> _shiftAction.value = End(currViewState.shiftToBeEnded)
            else -> logger.warn(TAG, "Cannot perform either add or remove action")
        }
    }

    private fun handleSuccess(shifts: List<Shift>) {
        if (shifts.isEmpty()) {
            logger.debug(TAG, "Setting the view state to ${prevViewState::class.java.simpleName}")
            _viewState.value = prevViewState
            if (prevViewState == EmptyState)
                _infoMsgData.value = R.string.info_shift_not_added
            return
        }

        val startedShift: Shift? = getStartedShift(shifts)
        if (startedShift == null) {
            val state = LoadedStateWithStart(shifts)
            _viewState.value = state
            prevViewState = state
        } else {
            val state = LoadedStateWithEnd(shifts, startedShift)
            _viewState.value = state
            prevViewState = state
        }
        logger.debug(TAG, "Setting the view state to ${prevViewState::class.java.simpleName}")
    }

    private fun getStartedShift(shifts: List<Shift>): Shift? = shifts.find {
        it.end.isEmpty()
    }

    fun clearResources() {
        _shiftAction.value = None
        _infoMsgData.value = 0
    }
}