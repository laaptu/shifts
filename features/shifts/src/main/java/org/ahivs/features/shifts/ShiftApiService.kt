package org.ahivs.features.shifts

import org.ahivs.shared.base.network.Network
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShiftApiService {
    @GET(Network.Urls.SHIFT_LIST)
    suspend fun getShifts(): List<Shift>

    @POST(Network.Urls.SHIFT_START)
    suspend fun startShift(@Body shiftData: ShiftData): String

    @POST(Network.Urls.SHIFT_END)
    suspend fun endShift(@Body shiftData: ShiftData): String
}