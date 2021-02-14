package org.ahivs.features.shifts.actions.domain

import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SHIFT_IN_PROGRESS
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SUCCESS_SHIFT_START
import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.testing.BaseTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ShiftActionRepoTest : BaseTest() {
    @Mock
    lateinit var shiftApiService: ShiftApiService

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var shiftData: ShiftData

    lateinit var shiftActionRepo: ShiftActionRepo

    @BeforeEach
    fun init() {
        MockitoAnnotations.initMocks(this)
        shiftActionRepo = ShiftActionRepo(shiftApiService, logger)
    }

    @DisplayName(
        """
        GIVEN valid response,
        WHEN startShift is called,
        THEN Success must be returned
    """
    )
    @Test
    fun validResponseForStartShiftReturnsSuccess() {
        runBlockingTest {
            `when`(shiftApiService.startShift(shiftData)).thenReturn(SUCCESS_SHIFT_START)
            val response = shiftActionRepo.startShift(shiftData)
            assertTrue(response is Success)
        }
    }

    @DisplayName(
        """
        GIVEN invalid response,
        WHEN startShift is called,
        THEN Failure must be returned with response message
    """
    )
    @Test
    fun invalidResponseForStartShiftReturnsFailure() {
        val invalidResponse = SHIFT_IN_PROGRESS
        runBlockingTest {
            `when`(shiftApiService.startShift(shiftData)).thenReturn(invalidResponse)
            val response = shiftActionRepo.startShift(shiftData)
            assertTrue(response is Failure)
            assertTrue((response as Failure).errorMsg == invalidResponse)
        }
    }

    @DisplayName(
        """
        GIVEN Exception ,
        WHEN startShift is called,
        THEN Failure must be returned with exception message
    """
    )
    @Test
    fun exceptionForStartShiftReturnsFailure() {
        val exceptionMsg = "Network failure"
        val exception = Exception(exceptionMsg)
        runBlockingTest {
            `when`(shiftApiService.startShift(shiftData)).thenAnswer {
                throw exception
            }
            val response = shiftActionRepo.startShift(shiftData)
            assertTrue(response is Failure)
            assertTrue((response as Failure).errorMsg == exceptionMsg)
        }
    }
}