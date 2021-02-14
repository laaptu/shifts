package org.ahivs.features.shifts.list.ui

import org.ahivs.features.shifts.data.Shift
import org.ahivs.features.shifts.list.domain.FetchError
import org.ahivs.features.shifts.list.domain.FetchSuccess
import org.ahivs.features.shifts.list.domain.ShiftsListRepo
import org.ahivs.features.shifts.utils.DateUtils
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.testing.BaseTest
import org.ahivs.shared.testing.getOrAwaitValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ShiftsListViewModelTest : BaseTest() {
    @Mock
    lateinit var shiftsListRepo: ShiftsListRepo

    @Mock
    lateinit var viewStateProvider: ListViewStateProvider

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var fetchSuccess: FetchSuccess

    @Mock
    lateinit var fetchError: FetchError

    @Mock
    lateinit var shift: Shift

    lateinit var viewModel: ShiftsListViewModel

    var shifts = listOf<Shift>()

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        `when`(viewStateProvider.getInitialShiftListViewState()).thenReturn(EmptyState)
        viewModel = ShiftsListViewModel(shiftsListRepo, viewStateProvider, logger)
    }

    @DisplayName(
        """
        GIVEN Shifts Fetch Error,
        WHEN fetchShifts() is called,
        THEN InfoMsg must be called with error msg id
    """
    )
    @Test
    fun shiftsFetchErrorMustShowErrorMsg() {
        runBlockingTest {
            `when`(shiftsListRepo.getShifts()).thenReturn(fetchError)
            viewModel.fetchShifts()
            val msgId = viewModel.infoMsg.getOrAwaitValue()
            assertTrue(msgId.peek() != 0)
        }
    }

    @DisplayName(
        """
        GIVEN Shifts Fetch with empty list of shifts,
        WHEN fetchShifts() is called,
        THEN InfoMsg must be called with msg id
    """
    )
    @Test
    fun shiftsFetchWithEmptyListInfoMsgMustBeDisplayed() {
        runBlockingTest {
            `when`(shiftsListRepo.getShifts()).thenReturn(fetchSuccess)
            `when`(fetchSuccess.shifts).thenReturn(emptyList())

            viewModel.fetchShifts()

            val msgId = viewModel.infoMsg.getOrAwaitValue()
            assertTrue(msgId.peek() != 0)
        }
    }

    @DisplayName(
        """
        GIVEN list of shifts without any started Shift,
        WHEN fetchShifts() is called,
        THEN viewState must change to LoadedWithStart
    """
    )
    @Test
    fun listOfShiftsWithoutAnyStartedThenViewStateMustBeLoadedWithStart() {
        runBlockingTest {
            invokeLoadedWithStartState()

            viewModel.fetchShifts()

            val viewState = viewModel.viewState.getOrAwaitValue()
            assertTrue(viewState is LoadedStateWithStart)
            assertEquals(shifts, (viewState as LoadedStateWithStart).shifts)
        }
    }

    private fun invokeLoadedWithStartState() {
        runBlockingTest {
            `when`(shiftsListRepo.getShifts()).thenReturn(fetchSuccess)
            `when`(shift.end).thenReturn(DateUtils.getCurrentTime())
            shifts = listOf(shift)
            `when`(fetchSuccess.shifts).thenReturn(shifts)
        }
    }

    private fun invokeLoadedWithEndState() {
        runBlockingTest {
            `when`(shiftsListRepo.getShifts()).thenReturn(fetchSuccess)
            `when`(shift.end).thenReturn("")
            shifts = listOf(shift)
            `when`(fetchSuccess.shifts).thenReturn(shifts)
        }
    }

    @DisplayName(
        """
        GIVEN list of shifts with a started shift,
        WHEN fetchShifts() is called,
        THEN viewState must change to LoadedWithEnd
    """
    )
    @Test
    fun listOfShiftsWithStartThenViewStateMustBeLoadedWithEnd() {
        runBlockingTest {
            invokeLoadedWithEndState()

            viewModel.fetchShifts()

            val viewState = viewModel.viewState.getOrAwaitValue()
            assertTrue(viewState is LoadedStateWithEnd)
            assertEquals(shifts, (viewState as LoadedStateWithEnd).shifts)
            assertEquals(shift, viewState.shiftToBeEnded)
        }
    }

    @DisplayName(
        """
        GIVEN viewState is Empty,
        WHEN invokeShiftStartEndAction,
        THEN ShiftAction must Be Start
    """
    )
    @Test
    fun emptyViewStateInvokeStartOnShiftStartEndAction() {
        runBlockingTest {

            `when`(viewStateProvider.getInitialShiftListViewState()).thenReturn(EmptyState)

            viewModel.invokeShiftStartEndAction()

            val shiftAction = viewModel.shiftAction.getOrAwaitValue()
            assertTrue(shiftAction.peek() is Start)
        }
    }

    @DisplayName(
        """
        GIVEN viewState is LoadedWithStart,
        WHEN invokeShiftStartEndAction,
        THEN ShiftAction must Be Start
    """
    )
    @Test
    fun loadedWithStartInvokeStartOnShiftStartEndAction() {
        runBlockingTest {

            invokeLoadedWithStartState()
            viewModel.fetchShifts()

            viewModel.invokeShiftStartEndAction()

            val shiftAction = viewModel.shiftAction.getOrAwaitValue()
            assertTrue(shiftAction.peek() is Start)
        }
    }

    @DisplayName(
        """
        GIVEN viewState is LoadedWithEnd,
        WHEN invokeShiftStartEndAction,
        THEN ShiftAction must Be End
    """
    )
    @Test
    fun loadedWithEndInvokeEndOnShiftStartEndAction() {
        runBlockingTest {

            invokeLoadedWithEndState()
            viewModel.fetchShifts()

            viewModel.invokeShiftStartEndAction()

            val shiftAction = viewModel.shiftAction.getOrAwaitValue()
            assertTrue(shiftAction.peek() is End)
            assertEquals(shift, (shiftAction.peek() as End).shiftToBeEnded)
        }
    }
}