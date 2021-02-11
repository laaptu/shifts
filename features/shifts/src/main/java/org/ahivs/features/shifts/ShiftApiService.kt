package org.ahivs.features.shifts

import org.ahivs.shared.base.network.Urls
import retrofit2.http.GET

interface ShiftApiService {
    @GET(Urls.SHIFT_LIST)
    suspend fun getShifts(): List<Shift>
}