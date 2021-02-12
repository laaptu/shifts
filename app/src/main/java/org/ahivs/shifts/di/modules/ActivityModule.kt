package org.ahivs.shifts.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.ahivs.features.shifts.actions.ShiftActionActivity
import org.ahivs.shared.base.di.ComponentScope

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    @ComponentScope
    abstract fun bindsShiftActionActivity(): ShiftActionActivity

}