package org.ahivs.shifts

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.ahivs.shared.base.di.DaggerBaseComponent
import org.ahivs.shifts.di.DaggerAppComponent
import javax.inject.Inject

class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .addBaseComponent(DaggerBaseComponent.create())
            .addContext(this)
            .build().injectOnApp(this)
    }
}