package org.ahivs.shared.base.di

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface BaseComponent {
    fun provideRetrofit(): Retrofit
}