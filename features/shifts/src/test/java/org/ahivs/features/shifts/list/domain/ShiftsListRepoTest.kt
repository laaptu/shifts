package org.ahivs.features.shifts.list.domain

import org.ahivs.features.shifts.data.Shift
import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.testing.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ShiftsListRepoTest : BaseTest() {
    @Mock
    lateinit var shiftApiService: ShiftApiService

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var shift: Shift

    lateinit var shiftListRepo: ShiftsListRepo
    lateinit var validShifts: List<Shift>

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        shiftListRepo = ShiftsListRepo(shiftApiService, logger)
        validShifts = listOf(shift)
    }

    @DisplayName(
        """
        GIVEN valid list of shifts,
        WHEN getShifts() is called,
        THEN FetchSuccess must be returned with valid list
    """
    )
    @Test
    fun validShiftReturnsFetchSuccess() {
        runBlockingTest {
            `when`(shiftApiService.getShifts()).thenReturn(validShifts)
            val response = shiftListRepo.getShifts()
            assertTrue(response is FetchSuccess)
            assertEquals(validShifts, (response as FetchSuccess).shifts)
        }
    }

    @DisplayName(
        """
        GIVEN NullPointerException,
        WHEN getShifts() is called,
        THEN FetchSuccess must be called with empty list
    """
    )
    @Test
    fun npeReturnsFetchSuccessWithEmptyList() {
        runBlockingTest {
            `when`(shiftApiService.getShifts()).thenAnswer {
                throw NullPointerException()
            }
            val response = shiftListRepo.getShifts()
            assertTrue(response is FetchSuccess)
            assertTrue((response as FetchSuccess).shifts.isEmpty())
        }
    }

    @DisplayName(
        """
        GIVEN Exception,
        WHEN getShifts() is called,
        THEN FetchError must be called with error message and it's exception
    """
    )
    @Test
    fun exceptionReturnsFetchError() {
        val errorMsg = "Network exception occurred"
        val exception = Exception(errorMsg)
        runBlockingTest {
            `when`(shiftApiService.getShifts()).thenAnswer {
                throw exception
            }
            val response = shiftListRepo.getShifts()
            assertTrue(response is FetchError)
            assertEquals(errorMsg, (response as FetchError).errorMsg)
            assertEquals(exception, response.exception)
        }
    }
}