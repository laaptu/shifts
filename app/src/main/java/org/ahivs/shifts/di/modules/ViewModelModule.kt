package org.ahivs.shifts.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ahivs.features.shifts.actions.ShiftActionViewModel
import org.ahivs.shared.base.di.viewmodel.ViewModelKey

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ShiftActionViewModel::class)
    abstract fun provideShiftActionViewModel(shiftActionViewModel: ShiftActionViewModel): ViewModel
}