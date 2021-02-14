package org.ahivs.features.shifts.actions.domain

import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SHIFT_IN_PROGRESS
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SHIFT_NOT_STARTED
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SUCCESS_SHIFT_END
import org.ahivs.features.shifts.actions.domain.ShiftActionResponse.Companion.SUCCESS_SHIFT_START
import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.features.shifts.data.ShiftData
import org.ahivs.shared.base.utils.Logger
import org.ahivs.shared.testing.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    inner class ShiftStartTests() {
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
                assertEquals(invalidResponse, (response as Failure).errorMsg)
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
                assertEquals(exceptionMsg, (response as Failure).errorMsg)
            }
        }
    }

    @Nested
    inner class ShiftEndTest {
        @DisplayName(
            """
        GIVEN valid response,
        WHEN endShift is called,
        THEN Success must be returned
        """
        )
        @Test
        fun validResponseForEndShiftReturnsSuccess() {
            runBlockingTest {
                `when`(shiftApiService.endShift(shiftData)).thenReturn(SUCCESS_SHIFT_END)
                val response = shiftActionRepo.endShift(shiftData)
                assertTrue(response is Success)
            }
        }

        @DisplayName(
            """
        GIVEN invalid response,
        WHEN endShift is called,
        THEN Failure must be returned with response message
        """
        )
        @Test
        fun invalidResponseForEndShiftReturnsFailure() {
            val invalidResponse = SHIFT_NOT_STARTED
            runBlockingTest {
                `when`(shiftApiService.endShift(shiftData)).thenReturn(invalidResponse)
                val response = shiftActionRepo.endShift(shiftData)
                assertTrue(response is Failure)
                assertEquals(invalidResponse, (response as Failure).errorMsg)
            }
        }

        @DisplayName(
            """
        GIVEN Exception ,
        WHEN endShift is called,
        THEN Failure must be returned with exception message
        """
        )
        @Test
        fun exceptionForEndShiftReturnsFailure() {
            val exceptionMsg = "Network failure"
            val exception = Exception(exceptionMsg)
            runBlockingTest {
                `when`(shiftApiService.endShift(shiftData)).thenAnswer {
                    throw exception
                }
                val response = shiftActionRepo.endShift(shiftData)
                assertTrue(response is Failure)
                assertEquals(exceptionMsg,(response as Failure).errorMsg)
            }
        }
    }
}