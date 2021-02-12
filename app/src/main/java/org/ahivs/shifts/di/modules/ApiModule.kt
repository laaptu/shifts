package org.ahivs.shifts.di.modules

import dagger.Module
import dagger.Provides
import org.ahivs.features.shifts.data.ShiftApiService
import org.ahivs.shared.base.di.AppScope
import retrofit2.Retrofit

@Module
class ApiModule {
    @Provides
    @AppScope
    fun getShiftApiService(retrofit: Retrofit): ShiftApiService =
        retrofit.create(ShiftApiService::class.java)
}