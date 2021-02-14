package org.ahivs.features.shifts.actions.ui

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.ahivs.features.shifts.actions.domain.Failure
import org.ahivs.features.shifts.actions.domain.ShiftActionRepo
import org.ahivs.features.shifts.actions.domain.ShiftDataProvider
import org.ahivs.features.shifts.actions.domain.Success
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.features.shifts.utils.DateUtils
import org.ahivs.shared.base.utils.LocationFetchError
import org.ahivs.shared.base.utils.LocationFetchSuccess
import org.ahivs.shared.base.utils.LocationFetcher
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.testing.BaseTest
import org.ahivs.shared.testing.getOrAwaitValue
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ShiftActionViewModelTest : BaseTest() {
    @Mock
    lateinit var shiftActionRepo: ShiftActionRepo

    @Mock
    lateinit var shiftDataProvider: ShiftDataProvider

    @Mock
    lateinit var locationFetcher: LocationFetcher

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var viewStateProvider: ViewStateProvider

    @Mock
    lateinit var locationFetchSuccess: LocationFetchSuccess

    @Mock
    lateinit var locationFetchError: LocationFetchError

    @Mock
    lateinit var shiftData: ShiftData

    @Mock
    lateinit var successResponse: Success

    @Mock
    lateinit var failureResponse: Failure

    lateinit var viewModel: ShiftActionViewModel

    private val latitude = 10.01
    private val longitude = 20.30

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        `when`(viewStateProvider.getInitialShiftActionState()).thenReturn(None)
        viewModel = ShiftActionViewModel(
            shiftActionRepo, shiftDataProvider,
            locationFetcher, logger, viewStateProvider
        )
        `when`(shiftDataProvider.getCurrentTime()).thenReturn(DateUtils.getCurrentTime())
        `when`(shiftDataProvider.createShiftData(anyOrNull(), anyOrNull(), any()))
            .thenReturn(shiftData)
    }

    private fun mockLocationFetchSuccess() {
        runBlockingTest {
            `when`(locationFetcher.fetchCurrentLocation()).thenReturn(locationFetchSuccess)
            `when`(locationFetchSuccess.latitude).thenReturn(latitude)
            `when`(locationFetchSuccess.longitude).thenReturn(longitude)
        }
    }

    private fun mockLocationFetchError() {
        runBlockingTest {
            `when`(locationFetcher.fetchCurrentLocation()).thenReturn(locationFetchError)
        }
    }

    @DisplayName(
        """
        GIVEN startTime is null,
        WHEN init() is called,
        THEN ViewState must be StartShift
    """
    )
    @Test
    fun initCallWithNullStartTime() {
        viewModel.init(startTime = null)
        val viewState: ViewState = viewModel.viewState.getOrAwaitValue()
        assertTrue(viewState is StartShift)
    }

    @DisplayName(
        """
        GIVEN startTime is not null,
        WHEN init() is called,
        THEN ViewState must be EndShift
    """
    )
    @Test
    fun initCallWithValidStartTime() {
        viewModel.init(startTime = DateUtils.getCurrentTime())
        val viewState: ViewState = viewModel.viewState.getOrAwaitValue()
        assertTrue(viewState is EndShift)
    }

    @DisplayName(
        """
        GIVEN initialised with null start time,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftActionRepo must be called for startShift
    """
    )
    @Test
    fun initCallWithNullStartTimeInvokesStartShift() {
        runBlockingTest {
            viewModel.init(startTime = null)
            viewModel.invokeShiftStartEndAction()
            verify(shiftActionRepo).startShift(any())
        }
    }

    @DisplayName(
        """
        GIVEN initialised with valid start time,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftActionRepo must be called for endShift
    """
    )
    @Test
    fun initCallWithStartTimeInvokesEndShift() {
        runBlockingTest {
            viewModel.init(startTime = DateUtils.getCurrentTime())
            viewModel.invokeShiftStartEndAction()
            verify(shiftActionRepo).endShift(any())
        }
    }

    @DisplayName(
        """
        GIVEN initialised with valid start time & enable location fetch & Location Fetch Success,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftDataProvider is called with valid latitude and longitude
    """
    )
    @Test
    fun locationFetchSuccessResultsShiftDataWithValidLocationValues() {
        runBlockingTest {
            viewModel.init(
                startTime = DateUtils.getCurrentTime(),
                enableLocationFetch = true
            )
            mockLocationFetchSuccess()
            viewModel.invokeShiftStartEndAction()
            verify(shiftDataProvider).createShiftData(
                eq(latitude.toString()),
                eq(longitude.toString()),
                any()
            )
        }
    }

    @DisplayName(
        """
        GIVEN initialised with valid start time & disable location fetch ,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftDataProvider is called with null latitude and longitude
    """
    )
    @Test
    fun onLocationFetchDisabledShiftDataWithNullLocationValues() {
        runBlockingTest {
            viewModel.init(
                startTime = DateUtils.getCurrentTime(),
                enableLocationFetch = false
            )
            viewModel.invokeShiftStartEndAction()
            verify(shiftDataProvider).createShiftData(
                eq(null),
                eq(null),
                any()
            )
        }
    }

    @DisplayName(
        """
        GIVEN initialised with valid start time & Location Fetch Error,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftDataProvider is called with null latitude and longitude
    """
    )
    @Test
    fun locationFetchErrorResultsShiftDataWithNullLocationValues() {
        runBlockingTest {
            viewModel.init(
                startTime = DateUtils.getCurrentTime(),
                enableLocationFetch = true
            )
            mockLocationFetchError()
            viewModel.invokeShiftStartEndAction()
            verify(shiftDataProvider).createShiftData(eq(null), eq(null), any())
        }
    }


    @DisplayName(
        """
        GIVEN Shift Start Success,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftResponse must contain success
    """
    )
    @Test
    fun withShiftStartSuccessResponseMustContainSuccess() {
        runBlockingTest {
            `when`(shiftActionRepo.startShift(any())).thenReturn(successResponse)
            viewModel.init(startTime = null)
            viewModel.invokeShiftStartEndAction()
            val response = viewModel.shiftResponse.getOrAwaitValue()
            assertTrue(response.peek() == successResponse)
        }
    }

    @DisplayName(
        """
        GIVEN Shift Start Failure,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftResponse must contain failure
    """
    )
    @Test
    fun withShiftStartFailureResponseMustContainFailure() {
        runBlockingTest {
            `when`(shiftActionRepo.startShift(any())).thenReturn(failureResponse)
            viewModel.init(startTime = null)
            viewModel.invokeShiftStartEndAction()
            val response = viewModel.shiftResponse.getOrAwaitValue()
            assertTrue(response.peek() == failureResponse)
        }
    }

    @DisplayName(
        """
        GIVEN Shift End Success,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftResponse must contain success
    """
    )
    @Test
    fun withShiftEndSuccessResponseMustContainSuccess() {
        runBlockingTest {
            `when`(shiftActionRepo.endShift(any())).thenReturn(successResponse)
            viewModel.init(startTime = DateUtils.getCurrentTime())
            viewModel.invokeShiftStartEndAction()
            val response = viewModel.shiftResponse.getOrAwaitValue()
            assertTrue(response.peek() == successResponse)
        }
    }

    @DisplayName(
        """
        GIVEN Shift End Failure,
        WHEN invokeShiftStartEndAction() is called,
        THEN ShiftResponse must contain failure
    """
    )
    @Test
    fun withShiftEndFailureResponseMustContainFailure() {
        runBlockingTest {
            `when`(shiftActionRepo.endShift(any())).thenReturn(failureResponse)
            viewModel.init(startTime = DateUtils.getCurrentTime())
            viewModel.invokeShiftStartEndAction()
            val response = viewModel.shiftResponse.getOrAwaitValue()
            assertTrue(response.peek() == failureResponse)
        }
    }
}